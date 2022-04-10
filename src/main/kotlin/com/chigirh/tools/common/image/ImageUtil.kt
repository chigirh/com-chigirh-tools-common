package com.chigirh.tools.common.image

import org.apache.commons.io.FileUtils
import java.io.FileNotFoundException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.name
import kotlin.io.path.notExists

object ImageUtil {
    fun readImageByteArray(
        path: String = "",
        fileName: String
    ) = Paths.get(path, fileName).let {
        if (it.notExists()) throw FileNotFoundException(it.toString())

        Pair(path, FileUtils.readFileToByteArray(it.toFile()))
    }

    fun readImageBase64(
        path: String = "",
        fileName: String
    ) = readImageByteArray(path, fileName).let {
        Pair(it.first, Base64.getEncoder().encodeToString(it.second))
    }

    fun fetchImages(path: String): List<Path> {
        val path = Paths.get(path)

        return if (!path.notExists()) {
            path.toList().filter {
                val extension = it.name.substring(it.name.lastIndexOf("."))
                imageExtensions.contains(extension)
            }
        } else listOf()
    }

    private val imageExtensions = setOf(".jpeg", ".jpg")
}
