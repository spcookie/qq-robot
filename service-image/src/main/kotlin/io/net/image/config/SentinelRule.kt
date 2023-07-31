package io.net.image.config

import com.alibaba.csp.sentinel.slots.block.RuleConstant
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager
import org.springframework.context.ApplicationStartupAware
import org.springframework.core.metrics.ApplicationStartup
import org.springframework.stereotype.Component

/**
 *@author Augenstern
 *@since 2023/7/28
 */
@Component
class SentinelRule : ApplicationStartupAware {

    companion object {
        const val IMG = "img"
        const val ST = "st"
    }

    init {
        flowRule()
    }

    private fun flowRule() {
        val flowRules = buildList {
            add(
                FlowRule().apply {
                    resource = IMG
                    count = 0.5
                    grade = RuleConstant.FLOW_GRADE_QPS
                    controlBehavior = RuleConstant.CONTROL_BEHAVIOR_WARM_UP_RATE_LIMITER
                    maxQueueingTimeMs = 1000 * 1
                }
            )
            add(
                FlowRule().apply {
                    resource = ST
                    count = 0.5
                    grade = RuleConstant.FLOW_GRADE_QPS
                    controlBehavior = RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER
                    maxQueueingTimeMs = 1000 * 2
                }
            )
        }
        FlowRuleManager.loadRules(flowRules)
    }

    override fun setApplicationStartup(applicationStartup: ApplicationStartup) {}
}