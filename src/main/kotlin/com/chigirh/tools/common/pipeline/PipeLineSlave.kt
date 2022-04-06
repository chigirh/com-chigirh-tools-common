package com.chigirh.tools.common.pipeline

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class PipeLineSlave<CMD : PipeLineCommand>(
    protected open val pipeLineName: String,
    protected open val option: CommandPipeLineOption,
    protected open val channel: Channel<PipeLineMessenger<CMD>>,
    protected open val slaveNumber: Int,
) {
    fun run() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val messenger = channel.receive()
                val command = messenger.command
                if (option.outputLog) {
                    logger.info(createLog("${messenger.taskName}-${command.commandName} start."))
                }
                execute(command)
                if (option.outputLog) {
                    logger.info(createLog("${messenger.taskName}-${command.commandName} end."))
                }
                messenger.success()
            }
        }
    }

    protected fun createLog(message: String) = "$pipeLineName[$slaveNumber] -- message:${message}"

    abstract fun execute(command: CMD)

    companion object {
        val logger: Logger = LoggerFactory.getLogger(PipeLineSlave.javaClass)
    }
}