package com.chigirh.tools.common.encryption

import com.chigirh.tools.common.encryption.AesExtension.decrypt
import com.chigirh.tools.common.encryption.AesExtension.encryptWithAES
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class AesExtensionTest {

    @Test
    @DisplayName("encrypt and decrypt")
    fun test() {
        AesExtension.init("yourEncryptKey01", "yourInitVector01")

        val encrypt = "your name is michel.".encryptWithAES()
        println(encrypt.raw())

        val decrypt = encrypt.decrypt()
        println(decrypt)

        Assertions.assertEquals("your name is michel.", decrypt)
    }
}