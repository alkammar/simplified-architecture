package com.plume.simplified.motion

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.get
import com.plume.entity.Motion
import com.plume.simplified.R
import com.plume.simplified.infra.BaseCard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MotionCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : BaseCard<Unit, Motion>(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true)
        color = typedValue.data
    }
    private var level = 0f

    override val viewModel by lazy {
        ViewModelProvider(findViewTreeViewModelStoreOwner()!!).get<MotionCardViewModel>()
    }

    override fun stateConfiguration() = Unit

    private val motionToggle: SwitchCompat by lazy { findViewById(R.id.motion_toggle) }
    private val motionSourceText: TextView by lazy { findViewById(R.id.motion_source) }

    init {
        inflate(context, R.layout.card_motion, this)
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.motion_background_color, typedValue, true)
        setCardBackgroundColor(typedValue.data)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        motionToggle.setOnCheckedChangeListener { view, isChecked ->
            viewModel.onToggleMotionAction(isChecked)
        }
    }

    override fun onStateUpdated(state: Motion) {
        when (state) {
            Motion.Disabled -> {
                motionToggle.updateSwitch(false)
                motionSourceText.text = "---"
                level = 0f
                invalidate()
            }

            Motion.NotDetected -> {
                motionToggle.updateSwitch(true)
                motionSourceText.text = "no motion detected"
                level = 0f
                invalidate()
            }

            is Motion.Detected -> {
                motionToggle.updateSwitch(true)
                motionSourceText.text = "${state.source} (${state.level})"
                level = state.level / 100f
                invalidate()
            }
        }
    }

    private fun SwitchCompat.updateSwitch(checked: Boolean) {
        if (checked != isChecked) {
            isChecked = checked
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0f, height.toFloat() * (1 - level), width.toFloat(), height.toFloat(), paint)
    }
}