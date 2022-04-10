package com.chigirh.tools.common.image

import org.apache.commons.io.FileUtils
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.notExists

object ImageUtil {
    fun Path.readImageByteArray() = Pair(this, FileUtils.readFileToByteArray(toFile()))

    fun Path.readImageBase64() = readImageByteArray().let {
        Pair(it.first, Base64.getEncoder().encodeToString(it.second))
    }

    fun fetchImages(path: String): List<Path> {
        val path = Paths.get(path)

        return if (!path.notExists()) {
            path.toFile().listFiles().toList().filter {
                println(it.name)
                println(it.name.lastIndexOf("."))
                val extension = it.name.substring(it.name.lastIndexOf("."))
                imageExtensions.contains(extension)
            }.map { it.toPath() }
        } else listOf()
    }

    private val imageExtensions = setOf(".jpeg", ".jpg")
}
