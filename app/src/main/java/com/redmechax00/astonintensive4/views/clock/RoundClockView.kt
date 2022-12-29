package com.redmechax00.astonintensive4.views.clock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.redmechax00.astonintensive4.R
import com.redmechax00.astonintensive4.utils.dp2px
import com.redmechax00.astonintensive4.utils.px2dp
import com.redmechax00.astonintensive4.views.clock.items.CircleParams
import com.redmechax00.astonintensive4.views.clock.items.clock_face.*
import com.redmechax00.astonintensive4.views.clock.items.hands.HandHourItem
import com.redmechax00.astonintensive4.views.clock.items.hands.HandItem
import com.redmechax00.astonintensive4.views.clock.items.hands.HandMinuteItem
import com.redmechax00.astonintensive4.views.clock.items.hands.HandSecondItem
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class RoundClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs), IndentTools, ColorTools, LengthTools {

    private lateinit var frameCircleItem: FrameCircleItem

    private lateinit var markSecondItem: MarkSecondItem
    private lateinit var markHourItem: MarkHourItem

    private lateinit var numbersItem: NumbersItem

    private lateinit var handSecondItem: HandSecondItem
    private lateinit var handMinuteItem: HandMinuteItem
    private lateinit var handHourItem: HandHourItem

    private lateinit var listOfCircleParams: List<CircleParams>

    override var paddingDp: Int = 0

    override var handSecondColor: Int = Color.BLACK
        get() = handSecondItem.color
        set(color) {
            field = color
            handSecondItem.color = color
        }

    override var handMinuteColor: Int = Color.BLACK
        get() = handMinuteItem.color
        set(color) {
            field = color
            handMinuteItem.color = color
        }

    override var handHourColor: Int = Color.BLACK
        get() = handHourItem.color
        set(color) {
            field = color
            handHourItem.color = color
        }

    override var handSecondLengthRadMultiplier: Float = 1f
        get() = handSecondItem.lengthRadMultiplier
        set(percentByRadius) {
            field = percentByRadius
            handSecondItem.lengthRadMultiplier = percentByRadius
        }

    override var handMinuteLengthRadMultiplier: Float = 1f
        get() = handMinuteItem.lengthRadMultiplier
        set(percentByRadius) {
            field = percentByRadius
            handMinuteItem.lengthRadMultiplier = percentByRadius
        }

    override var handHourLengthRadMultiplier: Float = 1f
        get() = handHourItem.lengthRadMultiplier
        set(percentByRadius) {
            field = percentByRadius
            handHourItem.lengthRadMultiplier = percentByRadius
        }

    init {
        initView()

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.RoundClockView, 0, 0)
        try {
            paddingDp =
                context.px2dp(
                    attributes.getDimension(
                        R.styleable.RoundClockView_android_padding,
                        0f
                    ).toInt()
                )
            handSecondLengthRadMultiplier =
                attributes.getFloat(
                    R.styleable.RoundClockView_second_hand_length_rad_multiplier,
                    handSecondLengthRadMultiplier
                )
            handMinuteLengthRadMultiplier =
                attributes.getFloat(
                    R.styleable.RoundClockView_minute_hand_length_rad_multiplier,
                    handMinuteLengthRadMultiplier
                )
            handHourLengthRadMultiplier =
                attributes.getFloat(
                    R.styleable.RoundClockView_hour_hand_length_rad_multiplier,
                    handHourLengthRadMultiplier
                )
            handSecondColor =
                attributes.getColor(R.styleable.RoundClockView_second_hand_color, handSecondColor)
            handMinuteColor =
                attributes.getColor(R.styleable.RoundClockView_minute_hand_color, handMinuteColor)
            handHourColor =
                attributes.getColor(R.styleable.RoundClockView_hour_hand_color, handHourColor)

        } finally {
            attributes.recycle()
        }
    }

    private fun initView() {
        frameCircleItem = FrameCircleItem()

        markSecondItem = MarkSecondItem()
        markHourItem = MarkHourItem()

        numbersItem = NumbersItem()

        handSecondItem = HandSecondItem()
        handMinuteItem = HandMinuteItem()
        handHourItem = HandHourItem()

        listOfCircleParams =
            listOf(
                frameCircleItem,
                markSecondItem,
                markHourItem,
                numbersItem,
                handSecondItem,
                handMinuteItem,
                handHourItem
            )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        refreshViewParams()
        drawClock(canvas)
    }

    private fun refreshViewParams() {
        val cX = width / 2f
        val cY = height / 2f
        val clockRadius = cX.coerceAtMost(cY) - context.dp2px(paddingDp)

        listOfCircleParams.forEach { view ->
            view.cX = cX
            view.cY = cY
            view.rad = clockRadius
        }
    }

    private fun drawClock(canvas: Canvas) {
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR]
        val minutes = calendar[Calendar.MINUTE]
        val seconds = calendar[Calendar.SECOND]

        val rotationAngleSec = seconds * handSecondItem.rotationAngle
        val rotationAngleMin =
            minutes * handMinuteItem.rotationAngle + handMinuteItem.rotationAngle / 60f * seconds
        val rotationAngleHour =
            hour * handHourItem.rotationAngle + handHourItem.rotationAngle / 60f * minutes

        canvas.drawCircleFrame(frameCircleItem)
        canvas.drawMarks(markHourItem)
        canvas.drawMarks(markSecondItem)
        canvas.drawNumbers(numbersItem)

        canvas.drawTick(handHourItem, rotationAngleHour)
        canvas.drawTick(handMinuteItem, rotationAngleMin)
        canvas.drawTick(handSecondItem, rotationAngleSec)

        postInvalidateDelayed(1000)
    }

    private fun Canvas.drawCircleFrame(item: FrameItem) {
        val paint = strokePaint(item.width, item.color)
        this.drawPath(item.path, paint)
    }

    private fun Canvas.drawMarks(item: MarkItem) {
        val paint = mainPaint(item.color)
        val rotationAngle = item.rotationAngle
        val count = (360 / rotationAngle).toInt()
        val ignoreCount = count / 12

        this.save()
        for (i in 0 until count) {
            this.rotate(rotationAngle, item.cX, item.cY)
            //Ignore each 5th mark for markSecond
            if (ignoreCount == 1 || (i + 1) % (ignoreCount) != 0) {
                this.drawPath(item.path, paint)
            }
        }
        this.restore()
    }

    private fun Canvas.drawNumbers(item: NumbersBaseItem) {
        val paint = Paint()
        paint.color = item.color
        paint.textSize = item.numSize
        val bounds = Rect()
        val numRad = item.numRad

        for (i in 1..12) {
            val text = i.toString()
            paint.getTextBounds(text, 0, text.length, bounds)
            val angle = PI / 6 * (i - 3)
            val x = (item.cX + cos(angle) * numRad - bounds.width() / 1.7).toFloat()
            val y = (item.cY + sin(angle) * numRad + bounds.height() / 2).toFloat()
            this.drawText(text, x, y, paint)
        }
    }

    private fun Canvas.drawTick(item: HandItem, rotationAngle: Float) {
        val color = item.color
        this.save()
        this.rotate(rotationAngle, item.cX, item.cY)
        this.drawPath(item.path, mainPaint(color))
        this.restore()
    }


    private fun mainPaint(color: Int): Paint {
        val paint = Paint()
        paint.color = color
        return paint
    }

    private fun strokePaint(strokeWidth: Float, color: Int): Paint {
        val paint = Paint()
        paint.color = color
        paint.strokeWidth = strokeWidth
        paint.style = Paint.Style.STROKE
        return paint
    }
}

