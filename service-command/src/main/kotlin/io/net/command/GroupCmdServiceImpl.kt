package io.net.command

import io.net.api.command.DubboGroupCmdServiceTriple
import io.net.api.command.GroupCmd
import io.net.api.command.MsgResult
import org.apache.dubbo.common.stream.StreamObserver
import org.apache.dubbo.config.annotation.DubboService
import java.util.concurrent.CompletableFuture

/**
 *@author Augenstern
 *@since 2023/7/7
 */
@DubboService
class GroupCmdServiceImpl : DubboGroupCmdServiceTriple.GroupCmdServiceImplBase() {
    override fun invoke(request: GroupCmd): MsgResult {
        return if (request.cmd == "st") {
            MsgResult.newBuilder().setMsg("你看nmd涩图").build()
        } else {
            MsgResult.newBuilder().setMsg(request.cmd).build()
        }
    }

//    override fun invoke(request: GroupCmd, responseObserver: StreamObserver<MsgResult>) {
//        if (request.cmd == "st") {
//            val msgResult = MsgResult.newBuilder().setMsg("你看nmd涩图").build()
//            responseObserver.onNext(msgResult)
//        } else {
//            val msgResult = MsgResult.newBuilder().setMsg(request.cmd).build()
//            responseObserver.onNext(msgResult)
//        }
//        responseObserver.onCompleted()
//    }
}