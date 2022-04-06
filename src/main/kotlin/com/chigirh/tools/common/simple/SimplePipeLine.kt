package com.chigirh.tools.common.simple

import com.chigirh.tools.common.pipeline.CommandPipeLineBase
import com.chigirh.tools.common.pipeline.PipeLineMessenger
import kotlinx.coroutines.channels.Channel
import org.springframework.stereotype.Component

@Component("simplePipeLine")
class SimplePipeLine : CommandPipeLineBase<SimpleCommand>(
    pipeLineName = "simplePipeLine",
    pipeLineSize = 100,
    commandNames = listOf("print")
) {
    override fun createSlave(
        channel: Channel<PipeLineMessenger<SimpleCommand>>,
        slaveNumber: Int
    ) = SimpleSlave(pipeLineName, option, channel, slaveNumber)

}