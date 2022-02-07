package com.jhbb.customview.component

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.jhbb.customview.R

class CustomCompoundView(context: Context, attrs: AttributeSet)
    : LinearLayout(context, attrs) {
         init {
             inflate(context, R.layout.custom_compound_view, this)

             val textView = findViewById<TextView>(R.id.tvDesc)
             val button = findViewById<Button>(R.id.btAction)

             val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomCompoundView)
             textView.text = attributes.getString(R.styleable.CustomCompoundView_description)
             button.text = attributes.getString(R.styleable.CustomCompoundView_button)
             attributes.recycle()
         }
}