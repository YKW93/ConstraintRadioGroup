# ConstraintRadioGroup
This is a custom RadioGroup library that inherits from ConstraintLayout. You can use ConstraintLayout attributes, easily changing text font and color for checked/unchecked statuses. RadioGroup uses CompoundButton so you can use RadioButton as well as CheckBox components.

## Usage
### Gradle
```gradle
dependencies {
    implementation 'com.wayne:constraint-radiogroup:1.0.0'
}
```

#### How to use
* xml
```xml
<com.wayne.constraintradiogroup.ConstraintRadioGroup
    android:layout_height="wrap_content"
    android:layout_width="match_parent"
    app:selected_font="@font/roboto_bold"
    app:selected_text_color="@android:color/holo_red_dark"
    app:unSelected_font="@font/roboto_light"
    app:unSelected_text_color="@android:color/black">

    <RadioButton
        android:checked="true"
        android:id="@+id/rb_text1"
        android:layout_height="wrap_content"
        android:layout_width="0dp"
        android:text="Text 1"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toStartOf="@+id/rb_text2"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <RadioButton
        android:id="@+id/rb_text2"
        android:layout_height="wrap_content"
        android:layout_width="0dp"
        android:text="Text 2"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toStartOf="@+id/rb_text3"
        app:layout_constraintStart_toEndOf="@+id/rb_text1"
        app:layout_constraintTop_toTopOf="parent" />

    <RadioButton
        android:id="@+id/rb_text3"
        android:layout_height="wrap_content"
        android:layout_width="0dp"
        android:text="Text 3"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@+id/rb_text2"
        app:layout_constraintTop_toTopOf="parent" />

</com.wayne.constraintradiogroup.ConstraintRadioGroup>
```
*kotlin
```kotlin
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
                // (checkedButton as RadioButton)
            }
        }
    }
}
```

#### Attributes
- `selected_font` (null)
- `unSelected_font` (null)
- `selected_text_color` (Color.BLACK) 
- `unSelected_text_color` (Color.BLACK) 

#### Important note while applying font
- You must create a directory to res/value/ and place the font file within that path.

## License  
```  
Copyright 2020 @Wayne

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
