package com.enesgemci.loginvuz.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.enesgemci.loginvuz.R
import com.enesgemci.loginvuz.core.extension.hide
import com.enesgemci.loginvuz.core.extension.show
import com.enesgemci.loginvuz.view.background.MDrawable
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

private const val SUCCESS_DELAY = 1000

class LoginButton : FrameLayout, CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val button
            by lazy { findViewById<TextView>(R.id.button) }

    private val progressBar
            by lazy { findViewById<ProgressBar>(R.id.progressBar) }

    private val animatedImageView
            by lazy { findViewById<AppCompatImageView>(R.id.animatedImageView) }

    private val defaultBg: Drawable
            by lazy {
                MDrawable.Builder(context)
                    .setRadius(resources.getDimension(R.dimen.common_radius))
                    .setBackgroundColorResId(R.color.blue)
                    .addType(MDrawable.Type.BACKGROUND)
                    .build()
            }

    private lateinit var animatedDrawable: AnimatedVectorDrawableCompat

    var state = State.Default
        set(value) {
            field = value

            when (value) {
                State.Default -> {
                    isClickable = true
                    background = defaultBg
                    progressBar.hide()
                    animatedImageView.hide()
                }
                State.Loading -> {
                    isClickable = false
                    progressBar.show()
                    animatedImageView.hide()
                }
                State.Success -> {
                    isClickable = false
                    startColorAnim()
                    progressBar.hide()
                    animatedImageView.show()
                    button.text = successText

                    if (::animatedDrawable.isInitialized) {
                        animatedDrawable.start()
                    }
                }
            }
        }

    var text: String = ""
        set(value) {
            field = value
            button.text = value
        }

    var successText: String = ""
    var successDelay: Int = SUCCESS_DELAY

    private var _onSuccess: (() -> Unit)? = null

    private var onClickListener: OnClickListener? = null

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.layout_login_button, this)
        background = defaultBg

        AnimatedVectorDrawableCompat.create(
            context,
            R.drawable.animated_vector_check
        )?.let {
            animatedDrawable = it
            animatedImageView.setImageDrawable(it)
            animatedDrawable.registerAnimationCallback(
                object : Animatable2Compat.AnimationCallback() {
                    override fun onAnimationEnd(drawable: Drawable?) {
                        launch {
                            delay(successDelay.toLong())
                            _onSuccess?.invoke()
                        }
                    }
                })
        }
    }

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {

        attrs?.let {
            val array =
                context.obtainStyledAttributes(it, R.styleable.LoginButton)

            try {
                val defaultText =
                    array.getString(R.styleable.LoginButton_default_text)
                successText =
                    array.getString(R.styleable.LoginButton_success_text)
                        .orEmpty()
                successDelay =
                    array.getInteger(
                        R.styleable.LoginButton_success_delay,
                        SUCCESS_DELAY
                    )
                button.text = defaultText.orEmpty()
            } finally {
                array.recycle()
            }
        }

        super.setOnClickListener {
            onClickListener?.onClick(this)
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        onClickListener = l
    }

    fun onSuccess(onSuccess: () -> Unit) {
        _onSuccess = onSuccess
    }

    private fun startColorAnim() {
        val colorFrom = ContextCompat.getColor(context, R.color.blue)
        val colorTo = ContextCompat.getColor(context, R.color.colorPrimary)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = (successDelay / 2).toLong()
        colorAnimation.addUpdateListener { animator ->
            background = MDrawable.Builder(context)
                .setRadius(resources.getDimension(R.dimen.common_radius))
                .setBackgroundColor(animator.animatedValue as Int)
                .addType(MDrawable.Type.BACKGROUND)
                .build()
        }
        colorAnimation.start()
    }

    enum class State {

        Default, Loading, Success
    }
}