package io.net.text.config

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
        const val CHAT = "chat"
    }

    init {
        flowRule()
    }

    private fun flowRule() {
        val flowRules = buildList {
            add(
                FlowRule().apply {
                    resource = CHAT
                    count = 0.15
                    grade = RuleConstant.FLOW_GRADE_QPS
                    controlBehavior = RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER
                }
            )
        }
        FlowRuleManager.loadRules(flowRules)
    }

    override fun setApplicationStartup(applicationStartup: ApplicationStartup) {}
}