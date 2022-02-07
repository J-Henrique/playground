package com.jhbb.customview.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.jhbb.customview.R
import org.w3c.dom.Text

class ExpandableCardView(context: Context, attrs: AttributeSet):
    LinearLayout(context, attrs) {

        init {
            inflate(context, R.layout.expandable_card, this)

            val descriptionContainer = findViewById<TextView>(R.id.cardDescription)
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.ExpandableCardView)

            descriptionContainer.text =
                    attributes.getText(R.styleable.ExpandableCardView_text)
            attributes.recycle()
        }
}