package io.net.api.command

class GroupCmdException(override val message: String?, override val cause: Throwable? = null) : RuntimeException()