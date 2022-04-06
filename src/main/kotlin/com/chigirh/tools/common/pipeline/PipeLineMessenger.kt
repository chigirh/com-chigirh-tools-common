package com.chigirh.tools.common.pipeline

import kotlinx.coroutines.channels.Channel

class PipeLineMessenger<CMD : PipeLineCommand>(
    val taskName: String,
    private val channel: Channel<Int>,
    val command: CMD,
) {
    suspend fun success() = channel.send(1)
    suspend fun fail() = channel.send(-1)
}
