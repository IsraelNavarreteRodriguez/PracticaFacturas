package com.example.practicafacturas.ui.factura.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Pinta el header del RecyclerView de facturas
 */
class HeaderDecoration(context : Context,parent : RecyclerView , @LayoutRes resId: Int) : RecyclerView.ItemDecoration() {
    private var mLayout: View = LayoutInflater.from(context).inflate(resId, parent, false)

    init{
        mLayout.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        mLayout.layout(parent.left, 0, parent.right, mLayout.measuredHeight)
        for (i in 0 until parent.childCount) {
            val view: View = parent.getChildAt(i)
            if (parent.getChildAdapterPosition(view) == 0) {
                c.save()
                val height: Int = mLayout.measuredHeight
                val top: Int = view.top - height
                c.translate(0F, top.toFloat())
                mLayout.draw(c)
                c.restore()
                break
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.set(0, mLayout.measuredHeight-20, 0, 0)
        } else {
            outRect.setEmpty()
        }
    }

}