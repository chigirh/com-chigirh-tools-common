package com.chigirh.tools.common.pipeline

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class PipeLineConsumer<CMD : PipeLineCommand>(
    open val pipeLineName: String,
    open val channel: Channel<PipeLineMessenger<CMD>>,
) {
    fun run() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val messenger = channel.receive()
                val command = messenger.command
                logger.info("$pipeLineName:${command.taskName}:${command.commandName} start.")
                execute(command)
                logger.info("$pipeLineName:${command.taskName}:${command.commandName} end.")
                messenger.success()
            }
        }
    }

    abstract fun execute(command: CMD)

    companion object {
        val logger: Logger = LoggerFactory.getLogger(PipeLineConsumer.javaClass)
    }
}