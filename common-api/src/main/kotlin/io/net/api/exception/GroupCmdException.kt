package io.net.api.exception


class GroupCmdException @JvmOverloads constructor(
    override val message: String? = null,
    override val cause: Throwable? = null
) : RuntimeException()