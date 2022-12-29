package com.redmechax00.astonintensive4.views.clock.items.hands

import android.graphics.Color
import android.graphics.Path
import com.redmechax00.astonintensive4.views.clock.items.FRAME_WIDTH_RAD_MULTIPLIER
import com.redmechax00.astonintensive4.views.clock.items.ITEMS_INDENT_TOP_FROM_FRAME_RAD_MULTIPLIER

class HandSecondItem : Path(), HandItem {

    override var rad = 0f
    override var cX = 0f
    override var cY = 0f

    override val rotationAngle: Float = 6f
    override var color = Color.rgb(222, 22, 22)
    override var lengthRadMultiplier: Float = 0.9f

    private val widthRadMultiplier: Float = 0.010f
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

            this.addCircle(cX, cY, rad / 25f, Direction.CW)

            val plumageWidth = width * 2.5f
            val plumageLength = tailLength * 1.4f

            //Top left corner
            this.moveTo(cX - plumageWidth, cY + tailLength)
            //Top right corner
            this.lineTo(cX + plumageWidth, cY + tailLength)
            //Bottom right corner
            this.lineTo(cX + plumageWidth, cY + tailLength + plumageLength)
            //Bottom left corner
            this.lineTo(cX - plumageWidth, cY + tailLength + plumageLength)
            this.close()

            return this
        }
}