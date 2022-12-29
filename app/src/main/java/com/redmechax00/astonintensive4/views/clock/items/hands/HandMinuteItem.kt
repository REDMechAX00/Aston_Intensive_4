package com.redmechax00.astonintensive4.views.clock.items.hands

import android.graphics.Color
import android.graphics.Path
import com.redmechax00.astonintensive4.views.clock.items.FRAME_WIDTH_RAD_MULTIPLIER
import com.redmechax00.astonintensive4.views.clock.items.ITEMS_INDENT_TOP_FROM_FRAME_RAD_MULTIPLIER

class HandMinuteItem : Path(), HandItem {

    override var rad = 0f
    override var cX = 0f
    override var cY = 0f

    override val rotationAngle: Float = 6f
    override var color = Color.BLACK
    override var lengthRadMultiplier: Float = 0.8f

    private val widthRadMultiplier: Float = 0.025f
    private val tailRadMultiplier: Float = 0.280f

    override val path: Path
        get() {
            val length = lengthRadMultiplier * rad
            val lengthPaddingTop = 0f
            val frameWidth = rad * FRAME_WIDTH_RAD_MULTIPLIER
            val paddingFromFrame = rad * ITEMS_INDENT_TOP_FROM_FRAME_RAD_MULTIPLIER + frameWidth
            val width = rad * widthRadMultiplier
            val tailLength = (length - paddingFromFrame - lengthPaddingTop) * tailRadMultiplier

            this.reset()
            //Bottom left corner
            this.moveTo(cX - width, cY + tailLength)
            //Top left corner
            this.lineTo(cX - width, cY - length + lengthPaddingTop + paddingFromFrame)
            //Top right corner
            this.lineTo(cX + width, cY - length + lengthPaddingTop + paddingFromFrame)
            //Bottom right corner
            this.lineTo(cX + width, cY + tailLength)
            this.close()
            return this
        }
}