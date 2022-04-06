package com.chigirh.tools.common.simple

import com.chigirh.tools.common.pipeline.CommandPipeLineOption
import com.chigirh.tools.common.pipeline.PipeLineMessenger
import com.chigirh.tools.common.pipeline.PipeLineSlave
import kotlinx.coroutines.channels.Channel

class SimpleSlave(
    override val pipeLineName: String,
    override val option: CommandPipeLineOption,
    override val channel: Channel<PipeLineMessenger<SimpleCommand>>,
    override val slaveNumber: Int,
) : PipeLineSlave<SimpleCommand>(pipeLineName, option, channel, slaveNumber) {
    override fun execute(command: SimpleCommand) {
        Thread.sleep(1000)
    }
}