package com.wayne.constraintradiogroup.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import com.wayne.constraintradiogroup.ConstraintRadioGroup
import com.wayne.constraintradiogroup.OnCheckedChangeListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        crg_custom.checkedChangeListener = object : OnCheckedChangeListener {
            override fun onCheckedChanged(
                group: ConstraintRadioGroup,
                checkedButton: CompoundButton
            ) {
                // TODO
//                (checkedButton as RadioButton)
            }
        }

        crg_check_box.checkedChangeListener = object : OnCheckedChangeListener {
            override fun onCheckedChanged(
                group: ConstraintRadioGroup,
                checkedButton: CompoundButton
            ) {
                // TODO
//                (checkedButton as CheckBox)
            }

        }
    }
}