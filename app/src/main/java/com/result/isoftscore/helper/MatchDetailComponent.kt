package com.result.isoftscore.helper

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.result.isoftscore.R
import kotlinx.android.synthetic.main.component_match_compare.view.*

class MatchDetailComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0): FrameLayout(context, attrs, defStyle) {

    var compareTitle: String? = ""
    set(value) {
        field = value ?: ""
        compare_title.text = value
    }

    var homeText: String? = ""
    set(value) {
        field = value ?: ""
        home_text.text = value
    }

    var awayText: String? = ""
    set(value) {
        field = value ?: ""
        away_text.text = value
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.component_match_compare, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                    R.styleable.component_matchdetail_compare_attributes, 0,0)

            compareTitle = typedArray.getString(
                    R.styleable.component_matchdetail_compare_attributes_compare_title)
            homeText = typedArray.getString(
                    R.styleable.component_matchdetail_compare_attributes_home_text)
            awayText = typedArray.getString(
                    R.styleable.component_matchdetail_compare_attributes_away_text)

            typedArray.recycle()
        }

    }
}