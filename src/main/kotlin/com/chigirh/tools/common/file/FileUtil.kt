package com.chigirh.tools.common.file

import org.apache.tomcat.util.http.fileupload.FileUtils
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.*

/**
 * File I/O Utils.
 * see:https://itsakura.com/kotlin-folder-copy
 * see:https://apricottail.com/?P=kotlin&PN=8
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
        charset: Charset = Charsets.UTF_8,
    ) = create(path, fileName).apply {
        writeText(text, charset)
    }

    fun forceDelete(path: Path) {
        if (path.isDirectory()) {
            FileUtils.deleteDirectory(path.toFile())
        } else {
            FileUtils.forceDelete(path.toFile())
        }
    }

    fun Path.write(
        text: String,
        charset: Charset = Charsets.UTF_8,
    ) = bufferedWriter(charset).use { bw ->
        bw.write(text)
        true
    }

    fun Path.write(
        texts: List<String>,
        charset: Charset = Charsets.UTF_8
    ) = bufferedWriter(charset).use { bw ->
        texts.forEach { bw.write(it) }
        true
    }

    fun Path.readText(charset: Charset = Charsets.UTF_8) = this.readLines(charset).joinToString()
}