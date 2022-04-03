package com.chigirh.tools.common.file

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists
import kotlin.io.path.writeText

/**
 * File I/O Utils.
 * see:https://itsakura.com/kotlin-folder-copy
 */
object FileUtil {
    fun create(
        path: String = "",
        fileName: String,
    ): Path {
        path.let {
            val p = Paths.get(it)
            if (Files.notExists(p)) {
                p.createDirectories()
            }
        }

        return Paths.get(path, fileName).let { if (it.notExists()) Files.createFile(it) else it }
    }

    fun createAndWrite(
        path: String = "",
        fileName: String,
        text: String,
    ) = create(path, fileName).apply {
        this.writeText(text)
    }
}