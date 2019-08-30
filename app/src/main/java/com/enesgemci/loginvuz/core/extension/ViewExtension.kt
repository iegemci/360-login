package com.enesgemci.loginvuz.core.extension

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

fun View.hide() {
    this.isVisible = false
}

fun View.show() {
    this.isVisible = true
}

fun View.invisible() {
    this.isInvisible = true
}

