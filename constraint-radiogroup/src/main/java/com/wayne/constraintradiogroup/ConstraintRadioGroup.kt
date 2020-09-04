package com.wayne.constraintradiogroup

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat


class ConstraintRadioGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    var checkedChangeListener: OnCheckedChangeListener? = null

    var selectedTextColor: Int = Color.BLACK
        private set
    var unSelectedTextColor: Int = Color.BLACK
        private set

    var selectedTextTypeface: Typeface? = null
        private set
    var unSelectedTextTypeface: Typeface? = null
        private set

    private var checkedButton: CompoundButton? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ConstraintRadioGroup)
        setTypedArray(typedArray)
    }

    private fun setTypedArray(typedArray: TypedArray) {
        selectedTextColor =
            typedArray.getColor(R.styleable.ConstraintRadioGroup_selected_text_color, Color.BLACK)
        unSelectedTextColor =
            typedArray.getColor(R.styleable.ConstraintRadioGroup_unSelected_text_color, Color.BLACK)

        try {
            selectedTextTypeface = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                typedArray.getFont(R.styleable.ConstraintRadioGroup_selected_font)
            } else {
                ResourcesCompat.getFont(
                    context,
                    typedArray.getResourceId(R.styleable.ConstraintRadioGroup_selected_font, 0)
                )
            }

            unSelectedTextTypeface = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                typedArray.getFont(R.styleable.ConstraintRadioGroup_unSelected_font)
            } else {
                ResourcesCompat.getFont(
                    context,
                    typedArray.getResourceId(
                        R.styleable.ConstraintRadioGroup_unSelected_font,
                        0
                    )
                )
            }
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }

        typedArray.recycle()
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child is CompoundButton) {
            if (child.isChecked) {
                checkedButton = child
            }
            setCheckedStateForView(child, false)

            child.setOnCheckedChangeListener { buttonView, isChecked ->
                setCheckedButton(buttonView)
            }
        }
        super.addView(child, index, params)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        checkedButton?.let {
            setCheckedStateForView(it, true)
        }
    }

    private fun setCheckedStateForView(compoundButton: CompoundButton, checked: Boolean) {
        if (checked) {
            compoundButton.typeface = selectedTextTypeface
            compoundButton.setTextColor(selectedTextColor)
        } else {
            compoundButton.typeface = unSelectedTextTypeface
            compoundButton.setTextColor(unSelectedTextColor)
        }
        compoundButton.isChecked = checked
    }

    private fun setCheckedButton(compoundButton: CompoundButton) {
        val changed = checkedButton != compoundButton

        if (changed) {
            checkedChangeListener?.onCheckedChanged(this, compoundButton)

            setCheckedStateForView(compoundButton, true)

            checkedButton?.let {
                setCheckedStateForView(it, false)
            }
            checkedButton = compoundButton
        }
    }
}