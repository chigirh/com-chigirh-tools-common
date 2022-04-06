package com.chigirh.tools.common

import com.chigirh.tools.common.pipeline.PipeLineTask
import com.chigirh.tools.common.pipeline.core.EnableCommandPipeline
import com.chigirh.tools.common.simple.SimpleCommand
import com.chigirh.tools.common.simple.SimplePipeLine
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableCommandPipeline
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    val context = runApplication<Application>(*args)

    val pipeline = context.getBean("simplePipeLine") as SimplePipeLine

    repeat(1000) {
        val text = "text:$it"
        val command = SimpleCommand(0, "print", "string-$it")
        pipeline.flowing(PipeLineTask("task$it", listOf(command)))
    }

//    exitProcess(0)
}
