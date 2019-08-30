package com.enesgemci.loginvuz.view.background

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.enesgemci.loginvuz.R

object MBackgroundHelper {

    fun getDrawable(context: Context, attrs: AttributeSet): Drawable {
        val m = getMDrawable(context, attrs)
        val drawable = m.get()
        drawable.setVisible(m.types.isNotEmpty(), true)

        return drawable
    }

    fun getMDrawable(context: Context, attrs: AttributeSet): MDrawable {
        val array = context.obtainStyledAttributes(attrs, R.styleable.MBackground)

        try {
            val backgroundColor = array
                .getResourceId(R.styleable.MBackground_background_color, -1)
            val pressedColor = array
                .getResourceId(R.styleable.MBackground_pressed_color, -1)
            val radius = array
                .getDimensionPixelOffset(R.styleable.MBackground_radius, -1)
            var radiusTopLeft = array
                .getDimensionPixelOffset(R.styleable.MBackground_radius_top_left, 0)
            var radiusTopRight = array
                .getDimensionPixelOffset(R.styleable.MBackground_radius_top_right, 0)
            var radiusBottomRight = array
                .getDimensionPixelOffset(R.styleable.MBackground_radius_bottom_right, 0)
            var radiusBottomLeft = array
                .getDimensionPixelOffset(R.styleable.MBackground_radius_bottom_left, 0)
            val borderWidth = array
                .getDimensionPixelSize(R.styleable.MBackground_border_width, 1)
            val borderColor = array
                .getResourceId(R.styleable.MBackground_border_color, -1)
            val shape = MDrawable.Shape.parse(
                array
                    .getInteger(R.styleable.MBackground_shape, MDrawable.Shape.RECTANGLE.index)
            )
            val types = array.getString(R.styleable.MBackground_background_type)

            val typeList = linkedSetOf<MDrawable.Type>()

            if (!types.isNullOrEmpty()) {
                if (types.contains(",")) {
                    val tt = types
                        .replace(" ", "")
                        .trim { it <= ' ' }
                        .split(",".toRegex())
                        .dropLastWhile { it.isEmpty() }
                        .toTypedArray()

                    tt
                        .map { MDrawable.Type.parse(it) }
                        .filterNot { typeList.contains(it) }
                        .forEach { typeList.add(it) }
                } else {
                    val type: MDrawable.Type?

                    type = try {
                        MDrawable.Type.parse(Integer.parseInt(types))
                    } catch (e: Exception) {
                        MDrawable.Type.parse(types)
                    }

                    if (!typeList.contains(type)) {
                        typeList.add(type)
                    }
                }
            }

            if (radius > -1) {
                radiusTopLeft = radius
                radiusTopRight = radius
                radiusBottomRight = radius
                radiusBottomLeft = radius
            }


            var builder = MDrawable.Builder(context)

            if (backgroundColor != -1) {
                if (!typeList.contains(MDrawable.Type.BACKGROUND)) {
                    typeList.add(MDrawable.Type.BACKGROUND)
                }

                builder = builder.setBackgroundColorResId(backgroundColor)
                    .setPressedColorResId(pressedColor)
            }
            if (borderColor != -1 && borderWidth > 0) {
                if (!typeList.contains(MDrawable.Type.BORDER)) {
                    typeList.add(MDrawable.Type.BORDER)
                }

                builder = builder.setBorderColorResId(borderColor)
                    .setBorderWidth(borderWidth.toFloat())
            }

            return builder.setTopLeftRadius(radiusTopLeft.toFloat())
                .setTopRightRadius(radiusTopRight.toFloat())
                .setBottomRightRadius(radiusBottomRight.toFloat())
                .setBottomLeftRadius(radiusBottomLeft.toFloat())
                .setShape(shape)
                .addType(typeList)
                .raw()
        } finally {
            array.recycle()
        }
    }
}
