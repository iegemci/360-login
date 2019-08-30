package com.enesgemci.loginvuz.core.extension

fun Boolean?.orFalse(): Boolean = this ?: false

fun Boolean?.orTrue(): Boolean = this ?: true
