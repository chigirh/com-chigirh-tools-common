package com.chigirh.tools.common.pipeline

data class CommandPipeLineOption(
    val isParallel: Boolean = true,
    val outputLog: Boolean = true,
    val exceedLimit: Long = Long.MAX_VALUE,
) {
    companion object {
        val DEFAULT = CommandPipeLineOption()
    }
}
