package io.net.api.filter

import io.net.api.MsgResult
import io.net.api.MsgResultChain
import io.net.api.exception.GroupCmdException
import org.apache.dubbo.common.constants.CommonConstants
import org.apache.dubbo.common.extension.Activate
import org.apache.dubbo.rpc.Filter
import org.apache.dubbo.rpc.Invocation
import org.apache.dubbo.rpc.Invoker
import org.apache.dubbo.rpc.Result
import org.slf4j.LoggerFactory

/**
 *@author Augenstern
 *@since 2023/7/16
 */
@Activate(CommonConstants.PROVIDER)
class DubboExceptionFilter : Filter {

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(DubboExceptionFilter::class.simpleName)
    }

    override fun invoke(invoker: Invoker<*>, invocation: Invocation): Result {
        val result = invoker.invoke(invocation)
        if (result.hasException()) {
            val exception = result.exception
            if (exception is GroupCmdException) {
                if (exception.cause != null) {
                    logger.error("business anomaly", exception.cause)
                }
                result.exception = null
                result.value = MsgResultChain.newBuilder()
                    .setCode(MsgResultChain.Code.BUSINESS_ANOMALY)
                    .addMsgResult(
                        MsgResult.newBuilder()
                            .setMsg(exception.message)
                            .build()
                    ).build()
            }
        }
        return result
    }
}