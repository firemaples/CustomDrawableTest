package com.firemaples.customdrawabletest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val locale = Locale.US

        val icon = CalendarIconDrawable(7, 10, Color.parseColor("#0183c3"), locale)
        iv_icon.setImageDrawable(icon)

        iv_icon.postDelayed({
            (iv_icon.drawable as? CalendarIconDrawable)?.apply {
                day = 20
                month = 2

                iv_icon.invalidate()
            }
        }, 5000)

        iv_icon2.setImageDrawable(CalendarIconDrawable(15, 11, Color.parseColor("#0183c3"), locale))
    }
}