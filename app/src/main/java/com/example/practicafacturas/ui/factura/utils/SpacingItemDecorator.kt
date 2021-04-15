package com.example.practicafacturas.ui.factura.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafacturas.R

/**
 * La funcion de esta clase es crear el separador para cada elemento del RecyclerView de factuas
 */
class SpacingItemDecorator(verticalSpaceHeight : Int, context : Context) :
    RecyclerView.ItemDecoration() {
    private val verticalSpaceHeight = verticalSpaceHeight
    private val divider = ContextCompat.getDrawable(context, R.drawable.line_divider)

    /**
     * Recoge el final de cada item del recycler para pintar el separador
     */
    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        super.getItemOffsets(outRect, itemPosition, parent)
        outRect.bottom = verticalSpaceHeight
    }

    /**
     * Pinta el separador
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params =
                child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin + verticalSpaceHeight / 2
            val bottom: Int = top + divider!!.intrinsicHeight + 2
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }
}