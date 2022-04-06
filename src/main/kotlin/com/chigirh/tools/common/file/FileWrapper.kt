package com.chigirh.tools.common.file

import java.nio.file.Path
import kotlin.io.path.bufferedWriter
import kotlin.io.path.readLines

/**
 * Simple file wrapper.
 * see:http://haneusagi.if.land.to/?P=kotlin&PN=8
 */
class FileWrapper(
    private val file: Path,
) {
    fun write(text: String) {
        file.bufferedWriter().use { bw ->
            bw.write(text)
            true
        }
    }

    fun write(texts: List<String>) {
        file.bufferedWriter().use { bw ->
            texts.forEach { bw.write(it) }
            true
        }
    }

    fun readLines() = file.readLines()
}