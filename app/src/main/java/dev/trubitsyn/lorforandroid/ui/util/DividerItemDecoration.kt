/*
 * Copyright (C) 2014 The Android Open Source Project
 * Copyright 2015-2016 Nikola Trubitsyn (with some modifications)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.trubitsyn.lorforandroid.ui.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val divider: Drawable?

    init {
        val style = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        divider = style.getDrawable(0)
        style.recycle()
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + divider!!.intrinsicHeight

            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
    }
}
