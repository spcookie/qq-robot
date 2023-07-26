package io.net.api.filter

import io.net.api.MsgCode
import io.net.api.MsgResult
import io.net.api.exception.GroupCmdException
import org.apache.dubbo.rpc.Filter
import org.apache.dubbo.rpc.Invocation
import org.apache.dubbo.rpc.Invoker
import org.apache.dubbo.rpc.Result

/**
 *@author Augenstern
 *@since 2023/7/16
 */
//@Activate(CommonConstants.PROVIDER)
class DubboExceptionFilter : Filter {
    override fun invoke(invoker: Invoker<*>, invocation: Invocation): Result {
        val result = invoker.invoke(invocation)
        if (result.hasException()) {
            val exception = result.exception
            if (exception is GroupCmdException) {
                result.exception = null
                result.value = MsgResult.newBuilder()
                    .setCode(MsgCode.BUSINESS_ANOMALY)
                    .setMsg(exception.message)
                    .build()
            }
        }
        return result
    }
}