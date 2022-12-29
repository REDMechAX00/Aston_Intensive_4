package com.redmechax00.astonintensive4

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.redmechax00.astonintensive4.utils.find
import com.redmechax00.astonintensive4.utils.setOnProgressChangedListener
import com.redmechax00.astonintensive4.views.clock.RoundClockView
import java.util.*

class MainActivity : AppCompatActivity() {

    private val btnRandomColor by find<Button>(R.id.main_btn_random_color)
    private val seekBarSec by find<SeekBar>(R.id.main_seek_bar_sec)
    private val seekBarMin by find<SeekBar>(R.id.main_seek_bar_min)
    private val seekBarHour by find<SeekBar>(R.id.main_seek_bar_hour)

    private val clockView by find<RoundClockView>(R.id.main_clock)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBarSec.progress = (clockView.handSecondLengthRadMultiplier * 100).toInt()
        seekBarMin.progress = (clockView.handMinuteLengthRadMultiplier * 100).toInt()
        seekBarHour.progress = (clockView.handHourLengthRadMultiplier * 100).toInt()

        initListeners()
    }

    private fun initListeners() {
        btnRandomColor.setOnClickListener {
            val rnd = Random()
            var color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            clockView.handSecondColor = color
            color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            clockView.handMinuteColor = color
            color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            clockView.handHourColor = color
        }

        seekBarSec.setOnProgressChangedListener { progress ->
            clockView.handSecondLengthRadMultiplier = progress
        }

        seekBarMin.setOnProgressChangedListener { progress ->
            clockView.handMinuteLengthRadMultiplier = progress
        }

        seekBarHour.setOnProgressChangedListener { progress ->
            clockView.handHourLengthRadMultiplier = progress
        }
    }
}