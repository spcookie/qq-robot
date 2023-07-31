package com.cn.augenstern.manager

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 *@author Augenstern
 *@date 2023/6/3
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [ArgsNumValid::class])
annotation class ArgsMin(
    val message: String,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<in Payload>> = [],
    val min: Int
)
