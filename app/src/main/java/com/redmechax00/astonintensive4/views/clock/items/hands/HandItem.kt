package com.redmechax00.astonintensive4.views.clock.items.hands

import android.graphics.Path
import com.redmechax00.astonintensive4.views.clock.items.CircleBaseItem

interface HandItem: CircleBaseItem {
    val path: Path
    val rotationAngle: Float
    var lengthRadMultiplier: Float
}