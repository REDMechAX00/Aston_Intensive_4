package com.redmechax00.astonintensive4.views.clock.items.clock_face

import android.graphics.Color
import android.graphics.Path
import com.redmechax00.astonintensive4.views.clock.items.FRAME_WIDTH_RAD_MULTIPLIER

class FrameCircleItem : Path(), FrameItem {

    override var rad = 0f
    override var cX = 0f
    override var cY = 0f

    override var color: Int = Color.BLACK

    override val width: Float
        get() = rad * FRAME_WIDTH_RAD_MULTIPLIER

    override val path: Path
        get() {
            this.reset()
            this.addCircle(cX, cY, rad - width / 2f, Direction.CW)
            return this
        }
}