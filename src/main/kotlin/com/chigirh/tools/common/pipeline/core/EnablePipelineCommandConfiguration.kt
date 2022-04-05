package com.chigirh.tools.common.pipeline.core

import com.chigirh.tools.common.pipeline.CommandPipeline
import com.chigirh.tools.common.pipeline.PipeLineCommand
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(PipeLineConfiguration::class)
annotation class EnableCommandPipeline

@Configuration
class PipeLineConfiguration(
    val pipeLines: List<CommandPipeline>,
) {
    @Bean
    fun pipelineInit() {
        pipeLines.forEach { it.start() }
    }
}