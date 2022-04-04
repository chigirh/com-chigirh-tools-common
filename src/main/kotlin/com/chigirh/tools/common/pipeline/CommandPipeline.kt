package com.chigirh.tools.common.pipeline

import kotlinx.coroutines.Job

interface CommandPipeline<CMD : PipeLineCommand> {
    fun start()

    fun flowing(task: PipeLineTask<CMD>): Job
}