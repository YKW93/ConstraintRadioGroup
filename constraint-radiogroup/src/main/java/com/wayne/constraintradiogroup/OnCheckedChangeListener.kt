package com.wayne.constraintradiogroup

import android.widget.CompoundButton
import androidx.annotation.IdRes


interface OnCheckedChangeListener {
    fun onCheckedChanged(group: ConstraintRadioGroup, checkedButton: CompoundButton)
}