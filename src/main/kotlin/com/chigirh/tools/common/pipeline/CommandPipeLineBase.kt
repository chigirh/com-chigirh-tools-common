package com.chigirh.tools.common.pipeline

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class CommandPipeLineBase<CMD : PipeLineCommand>(
    private val isParallel: Boolean = true,
    private val pipeLineName: String,
    private val pipeLineSize: Int,
    private val commandNames: List<String>,
    private val channels: MutableMap<String, Channel<PipeLineMessenger<CMD>>> = HashMap()
) : CommandPipeline {
    private var isActive: Boolean = false

    override fun start() {
        if (isActive) return

        logger.info("$pipeLineName start. size=$pipeLineSize")

        isActive = true
        for (command in commandNames) {
            repeat(pipeLineSize) {
                val channel = Channel<PipeLineMessenger<CMD>>()
                channels[command] = channel
                val task = consumer(channel)
                task.run()
            }
        }
    }

    abstract fun consumer(channel: Channel<PipeLineMessenger<CMD>>): PipeLineConsumer<CMD>

    fun flowing(task: PipeLineTask<CMD>) =
        CoroutineScope(Dispatchers.Default).launch {
            if (isParallel) parallel(task) else series(task)
        }

    private suspend fun parallel(task: PipeLineTask<CMD>) {
        val noticeChannel = Channel<Int>()
        task.commands.forEach {
            CoroutineScope(Dispatchers.Default).launch {
                val messenger = PipeLineMessenger(noticeChannel, it)
                channels[it.commandName]!!.send(messenger)
            }
        }
        repeat(task.commands.size) {
            noticeChannel.receive()
        }
    }

    private suspend fun series(task: PipeLineTask<CMD>) {
        val noticeChannel = Channel<Int>()
        task.commands.sortedBy { it.sequence }.forEach {
            CoroutineScope(Dispatchers.Default).launch {
                val messenger = PipeLineMessenger(noticeChannel, it)
                channels[it.commandName]!!.send(messenger)
            }
            noticeChannel.receive()
        }
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(CommandPipeLineBase.javaClass)
    }
}

