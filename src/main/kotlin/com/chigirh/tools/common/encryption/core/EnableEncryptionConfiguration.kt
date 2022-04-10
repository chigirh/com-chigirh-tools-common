package com.chigirh.tools.common.encryption.core

import com.chigirh.tools.common.encryption.AesExtension
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.env.Environment
import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EncryptionConfiguration::class)
annotation class EnableEncryption

/**
 * NOTE:AES
 * - chigirh.encryption.aes.key
 * - chigirh.encryption.aes.initKey
 * - chigirh.encryption.aes.algorithm
 */
@Configuration
class EncryptionConfiguration(
    val environment: Environment,
) {
    @Bean
    fun aesInit() {
        val key = environment.getProperty("chigirh.encryption.aes.key")
        val initKey = environment.getProperty("chigirh.encryption.aes.initKey")

        if (key?.isBlank() != false || initKey?.isBlank() != false) {
            return
        }

        val algorithm = environment.getProperty("chigirh.encryption.aes.algorithm")
        if (algorithm?.isBlank() != false) {
            AesExtension.init(key, initKey)
        } else AesExtension.init(key, initKey, algorithm)
    }
}