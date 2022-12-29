package com.redmechax00.astonintensive4.views.clock.items.clock_face

import android.graphics.Color
import android.graphics.Path
import com.redmechax00.astonintensive4.views.clock.items.FRAME_WIDTH_RAD_MULTIPLIER
import com.redmechax00.astonintensive4.views.clock.items.ITEMS_INDENT_TOP_FROM_FRAME_RAD_MULTIPLIER

class MarkHourItem : Path(), MarkItem {

    override var rad = 0f
    override var cX = 0f
    override var cY = 0f

    override var color = Color.BLACK
    override val rotationAngle: Float = 30f

    private val widthRadMultiplier: Float = 0.022f
    private val heightRadMultiplier: Float = 0.080f

    override val path: Path
        get() {
            val frameWidth = rad * FRAME_WIDTH_RAD_MULTIPLIER
            val marginFromFrame = rad * ITEMS_INDENT_TOP_FROM_FRAME_RAD_MULTIPLIER + frameWidth
            val widthTop = rad * widthRadMultiplier
            val widthBottom = (rad * widthRadMultiplier) / 1.6f
            val height = rad * heightRadMultiplier

            this.reset()
            //Top left corner
            this.moveTo(cX - widthTop, cY - rad + marginFromFrame)
            //Top right corner
            this.lineTo(cX + widthTop, cY - rad + marginFromFrame)
            //Bottom right corner
            this.lineTo(cX + widthBottom, cY - rad + height + marginFromFrame)
            //Bottom left corner
            this.lineTo(cX - widthBottom, cY - rad + height + marginFromFrame)
            this.close()
            return this
        }
}