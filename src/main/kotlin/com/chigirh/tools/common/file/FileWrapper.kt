package com.chigirh.tools.common.file

import com.chigirh.tools.common.file.FileUtil.write
import java.nio.charset.Charset
import java.nio.file.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

/**
 * Simple file wrapper.
 * see:http://haneusagi.if.land.to/?P=kotlin&PN=8
 */
class FileWrapper(
    private val file: Path,
    private val charset: Charset = Charsets.UTF_8
) {
    fun write(text: String) = file.write(text, charset)

    fun write(texts: List<String>) = file.write(texts, charset)

    fun readLines() = file.readLines(charset)

    fun readText() = file.readText(charset)
}