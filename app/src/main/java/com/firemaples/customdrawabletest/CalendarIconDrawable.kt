package com.firemaples.customdrawabletest

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.TypedValue
import java.text.SimpleDateFormat
import java.util.*

class CalendarIconDrawable(
    var day: Int,
    var month: Int,
    private var foregroundColor: Int,
    var locale: Locale
) : Drawable() {

    private companion object {
        private const val HEIGHT_SCALE = 35f
        private const val WIDTH_SCALE = 24f
        private const val DAY_TEXT_SIZE = 14f
        private const val MONTH_BOUND_HEIGHT = 10f
        private const val MONTH_TEXT_SIZE = 7f
        private const val DIVIDER_ALPHA = 0.5f
    }

    private val monthFormat: SimpleDateFormat by lazy { SimpleDateFormat("MMM", locale) }

//    private val dividerHeight: Float = dpToPx(context, 1)
//    private val dayTextSize: Float = dpToPx(context, DAY_TEXT_SIZE)
//    private val monthBoundHeight: Float = dpToPx(context, 12)
//    private val monthTextSize: Float = dpToPx(context, 8)

    private val dividerPaint: Paint = Paint().apply {
        color = (foregroundColor and 0x00ffffff) or ((255f * DIVIDER_ALPHA).toInt() shl 24)
        isAntiAlias = true
    }
    private val dayPaint: Paint = Paint().apply {
        color = foregroundColor
        isAntiAlias = true
    }
    private val monthPaint: Paint = Paint().apply {
        color = foregroundColor
        isAntiAlias = true
    }

    private val dayText: String
        get() = String.format("%02d", day)
    private val monthText: String
        get() {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.MONTH, month)
            }
            return monthFormat.format(calendar.time).toUpperCase(locale)
        }

    override fun draw(canvas: Canvas) {
        drawRelated(canvas)
    }

    private fun drawRelated(canvas: Canvas) {
        val width: Int = bounds.width()
        val height: Int = bounds.height()

        // Draw divider
        val dividerHeight = height.toFloat() * 1 / HEIGHT_SCALE
        val lineMarginTop = height / 2f - dividerHeight / 2
        dividerPaint.strokeWidth = dividerHeight
        canvas.drawLine(0f, lineMarginTop, width.toFloat(), lineMarginTop, dividerPaint)

        // Draw day
        val dayBounds = Rect(0, 0, width, lineMarginTop.toInt())
        dayPaint.textSize = height.toFloat() * DAY_TEXT_SIZE / HEIGHT_SCALE
        canvas.drawTextCentered(dayText, dayPaint, dayBounds)

        // Draw month
        val bottomOfDivider = lineMarginTop + dividerHeight
        val monthBoundHeight = height.toFloat() * MONTH_BOUND_HEIGHT / HEIGHT_SCALE
        val monthBounds = Rect(
            0, bottomOfDivider.toInt(), width,
            (bottomOfDivider + monthBoundHeight).toInt()
        )
        monthPaint.textSize = height.toFloat() * MONTH_TEXT_SIZE / HEIGHT_SCALE
        canvas.drawTextCentered(monthText, monthPaint, monthBounds)
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    override fun getOpacity(): Int = PixelFormat.OPAQUE

//    private fun dpToPx(context: Context, dp: Int) = TypedValue.applyDimension(
//        TypedValue.COMPLEX_UNIT_DIP,
//        dp.toFloat(),
//        context.resources.displayMetrics
//    )

    private fun Canvas.drawTextCentered(text: String, paint: Paint, rect: Rect) {
        val textBounds = Rect()
        paint.getTextBounds(text, 0, text.length, textBounds)
        drawText(
            text,
            rect.exactCenterX() - textBounds.exactCenterX(),
            rect.exactCenterY() - textBounds.exactCenterY(),
            paint
        )
    }
}