package com.cn.augenstern.manager

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

/**
 *@author Augenstern
 *@date 2023/6/3
 */
class ArgsNumValid : ConstraintValidator<ArgsMin, List<String>> {

    private var min: Int = 0

    override fun initialize(constraintAnnotation: ArgsMin?) {
        constraintAnnotation?.also { min = it.min }
    }

    override fun isValid(value: List<String>?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            return false
        }
        return value.size >= min
    }
}