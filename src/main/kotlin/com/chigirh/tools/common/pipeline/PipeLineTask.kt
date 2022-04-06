package com.chigirh.tools.common.pipeline

data class PipeLineTask<CMD : PipeLineCommand>(
    val taskName: String,
    val commands: List<CMD>
)

abstract class PipeLineCommand(
    open val sequence: Int = 0,
    open val commandName: String,
)

