package com.chigirh.tools.common.simple

import com.chigirh.tools.common.pipeline.PipeLineCommand

data class SimpleCommand(
    override val sequence: Int,
    override val commandName: String,
    val text: String,
) : PipeLineCommand(sequence, commandName)
