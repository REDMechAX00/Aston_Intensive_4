package com.redmechax00.astonintensive4.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.SeekBar

fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

fun <T : View> Activity.find(idRes: Int) = unsafeLazy<T> { findViewById(idRes) }

fun Context.dp2px(value: Int): Int = (resources.displayMetrics.density * value).toInt()

fun Context.px2dp(value: Int): Int = (value / resources.displayMetrics.density).toInt()

fun SeekBar.setOnProgressChangedListener(onProgress: (progress: Float) -> Unit) {
    this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onProgress.invoke(progress / 100f)
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })
}