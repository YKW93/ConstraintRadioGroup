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
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children


class ConstraintRadioGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var checkedChangeListener: OnCheckedChangeListener? = null

    var selectedTextColor: Int = Color.BLACK
        private set
    var unSelectedTextColor: Int = Color.BLACK
        private set

    var selectedTextTypeface: Typeface? = null
        private set
    var unSelectedTextTypeface: Typeface? = null
        private set

    private val checkedButtons: ArrayList<CompoundButton> = arrayListOf()

    private var isMultiple: Boolean = false

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ConstraintRadioGroup)
        setTypedArray(typedArray)
    }

    private fun setTypedArray(typedArray: TypedArray) {
        selectedTextColor =
            typedArray.getColor(R.styleable.ConstraintRadioGroup_selected_text_color, Color.BLACK)
        unSelectedTextColor =
            typedArray.getColor(R.styleable.ConstraintRadioGroup_unSelected_text_color, Color.BLACK)

        isMultiple = typedArray.getBoolean(R.styleable.ConstraintRadioGroup_is_multiple_select, false)

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
            if (child.isChecked && checkedButtons.count { it is RadioButton } == 1) {
                checkedButtons.add(child)
            }
            setCheckedStateForView(child, false)

            child.setOnCheckedChangeListener { buttonView, isChecked ->
                setCheckedButton(buttonView, isChecked)
            }
        }
        super.addView(child, index, params)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if(children.all { it is RadioButton }) {
            isMultiple = false
            // Default choose first
            checkedButtons.firstOrNull()?.let {
                checkedButtons.clear()
                checkedButtons.add(it)
            }
            checkedButtons.singleOrNull()?.let {
                setCheckedStateForView(it, true)
            }
        } else if(!isMultiple) {
            checkedButtons.singleOrNull()?.let {
                setCheckedStateForView(it, true)
            }
        } else {
            checkedButtons.forEach {
                setCheckedStateForView(it, true)
            }
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

    private fun setCheckedButton(compoundButton: CompoundButton, isChecked: Boolean) {
        if(!isMultiple) {
            val changed = checkedButtons.singleOrNull() != compoundButton

            if (changed) {
                checkedChangeListener?.onCheckedChanged(this, compoundButton, isChecked)

                setCheckedStateForView(compoundButton, true)

                checkedButtons.singleOrNull()?.let {
                    setCheckedStateForView(it, false)
                }
                checkedButtons.clear()
                checkedButtons.add(compoundButton)
            } else if (compoundButton is CheckBox) {
                checkedChangeListener?.onCheckedChanged(this, compoundButton, isChecked)
                setCheckedStateForView(compoundButton, isChecked)
                if (isChecked) {
                    checkedButtons.clear()
                    checkedButtons.add(compoundButton)
                } else checkedButtons.remove(compoundButton)
            }
        } else {
            if (compoundButton is RadioButton && children.count { it is RadioButton } > 1) {
                val changed = checkedButtons.firstOrNull { it is RadioButton } != compoundButton

                if (changed) {
                    checkedChangeListener?.onCheckedChanged(this, compoundButton, isChecked)

                    setCheckedStateForView(compoundButton, true)

                    checkedButtons.firstOrNull { it is RadioButton }?.let {
                        setCheckedStateForView(it, false)
                    }
                    checkedButtons.removeAll { it is RadioButton }
                    checkedButtons.add(compoundButton)
                }
            } else {
                checkedChangeListener?.onCheckedChanged(this, compoundButton, isChecked)

                setCheckedStateForView(compoundButton, isChecked)

                if (isChecked) checkedButtons.add(compoundButton)
                else checkedButtons.remove(compoundButton)
            }
        }
    }
}