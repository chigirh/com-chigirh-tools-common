package com.chigirh.tools.common.encryption

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AesExtension {
    private lateinit var key: SecretKeySpec
    private lateinit var iv: IvParameterSpec
    private lateinit var algorithm: String

    private var initialized = false

    fun init(
        secretKey: String,
        initializationKey: String,
        algorithm: String = DEFAULT_ALGORITHM,
    ) {
        if (initialized) return
        this.key = SecretKeySpec(secretKey.toByteArray(), "AES")
        this.iv = IvParameterSpec(initializationKey.toByteArray())
        this.algorithm = algorithm
        initialized = true
    }

    fun String.encryptWithAES(): AesEncrypt {

        if (!initialized) throw NotImplementedError("chigirh.encryption.aes")

        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)

        val byteResult = cipher.doFinal(this.toByteArray())

        return AesEncrypt(Base64.getEncoder().encodeToString(byteResult))
    }

    fun AesEncrypt.decrypt(): String {

        if (!initialized) throw NotImplementedError("chigirh.encryption.aes")
        
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        val byteResult = Base64.getDecoder().decode(this.raw())
        return String(cipher.doFinal(byteResult))
    }

    private const val DEFAULT_ALGORITHM = "AES/CBC/PKCS5Padding"
}

data class AesEncrypt(
    private val value: String,
) {
    fun raw() = value
}