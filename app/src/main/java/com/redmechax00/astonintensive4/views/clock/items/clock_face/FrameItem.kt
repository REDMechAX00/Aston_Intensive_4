package com.redmechax00.astonintensive4.views.clock.items.clock_face

import android.graphics.Path
import com.redmechax00.astonintensive4.views.clock.items.CircleBaseItem

interface FrameItem: CircleBaseItem {
    val path: Path
    val width: Float
}