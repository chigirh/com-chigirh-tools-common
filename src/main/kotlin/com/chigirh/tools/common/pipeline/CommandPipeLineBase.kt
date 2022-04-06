package com.chigirh.tools.common.pipeline

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

abstract class CommandPipeLineBase<CMD : PipeLineCommand>(
    protected val pipeLineName: String,
    protected val pipeLineSize: Int,
    protected val commandNames: List<String>,
    protected val option: CommandPipeLineOption = CommandPipeLineOption.DEFAULT,
    private val channels: MutableMap<String, Channel<PipeLineMessenger<CMD>>> = HashMap()
) : CommandPipeline {
    private var isActive: Boolean = false

    override fun start() {
        if (isActive) return

        logger.info("$pipeLineName start. size=$pipeLineSize")

        isActive = true
        for (command in commandNames) {
            val channel = Channel<PipeLineMessenger<CMD>>()
            repeat(pipeLineSize) {
                channels[command] = channel
                val task = createSlave(channel, it)
                task.run()
            }
        }
    }

    abstract fun createSlave(
        channel: Channel<PipeLineMessenger<CMD>>,
        slaveNumber: Int,
    ): PipeLineSlave<CMD>

    /**
     * Flowing for pipe line.
     *
     * @param task:pipe line task
     * @return pipe line Job
     */
    fun flowing(task: PipeLineTask<CMD>) =
        CoroutineScope(Dispatchers.Default).launch {
            if (option.isParallel) parallel(task) else series(task)
        }

    private suspend fun parallel(task: PipeLineTask<CMD>) {
        val noticeChannel = Channel<Int>()

        val startedAt = LocalDateTime.now()

        task.commands.forEach {
            CoroutineScope(Dispatchers.Default).launch {
                val messenger = PipeLineMessenger(task.taskName, noticeChannel, it)
                channels[it.commandName]!!.send(messenger)
            }
        }
        repeat(task.commands.size) {
            noticeChannel.receive()
        }

        val endedAt = LocalDateTime.now()

        val processingTime = ChronoUnit.MILLIS.between(startedAt, endedAt)
        if (option.outputLog && option.exceedLimit < processingTime) {
            logger.info("$pipeLineName -- message:exceed log ${task.taskName}->${processingTime}ms")
        }
    }

    private suspend fun series(task: PipeLineTask<CMD>) {
        val noticeChannel = Channel<Int>()
        task.commands.sortedBy { it.sequence }.forEach {
            CoroutineScope(Dispatchers.Default).launch {
                val messenger = PipeLineMessenger(task.taskName, noticeChannel, it)
                channels[it.commandName]!!.send(messenger)
            }
            noticeChannel.receive()
        }
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(CommandPipeLineBase.javaClass)
    }
}
