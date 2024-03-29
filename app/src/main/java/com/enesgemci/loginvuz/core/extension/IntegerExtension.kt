package com.enesgemci.loginvuz.core.extension

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * Created by enesgemci on 04/10/2017.
 */
fun Int.dpToPx(context: Context?): Int {
    return context?.let {
        val displayMetrics = it.resources.displayMetrics
        val px =
            Math.round((this * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)).toFloat())
        px
    } ?: this
}

fun Int.spToPx(context: Context): Float {
    //        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    //        int px = Math.round(sp * (displayMetrics.scaledDensity / DisplayMetrics.DENSITY_DEFAULT));
    //        return px;
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        context.resources.displayMetrics
    )
}