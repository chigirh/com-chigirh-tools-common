package com.chigirh.tools.common

import com.chigirh.tools.common.pipeline.core.EnableCommandPipeline
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.system.exitProcess

@EnableCommandPipeline
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
    exitProcess(0)
}
