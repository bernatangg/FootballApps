package com.result.isoftscore.helper

import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView
import android.view.View

class BottomDecoration(private val pixels: Int): RecyclerView.ItemDecoration() {
    constructor(context: Context, @DimenRes id: Int) : this(context.resources.getDimensionPixelSize(id))

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val params: RecyclerView.LayoutParams = view.layoutParams as RecyclerView.LayoutParams
        val position = params.viewAdapterPosition

        if (position == state.itemCount - 1) {
            outRect.bottom = 0
        } else {
            outRect.bottom = pixels
        }
    }
}