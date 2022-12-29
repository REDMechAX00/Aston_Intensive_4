package com.redmechax00.astonintensive4.views.clock.items.clock_face

import android.graphics.Color

class NumbersItem : NumbersBaseItem {

    override var rad = 0f
    override var cX = 0f
    override var cY = 0f

    override var color: Int = Color.BLACK

    override val numSize: Float
        get() = rad * 0.18f

    override val numRad: Float
        get() = rad * 0.7f
}