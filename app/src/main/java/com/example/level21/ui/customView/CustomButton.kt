package com.example.level21.ui.customView

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.level21.R
import com.example.level21.R.styleable.CustomButton
import kotlin.math.roundToInt

private const val DEF_STYLE_RES = R.style.CustomButton

class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs, 0, DEF_STYLE_RES) {

    private val backgroundColor: Int
    private val paddings: Float
    private var image: Bitmap?
    private val backgroundRect = RectF()
    private var buttonHeight = 0f
    private var cornerRadius = 0f
    private val paint = Paint(Paint.SUBPIXEL_TEXT_FLAG)
    private var startX = 0F
    private var label = ""

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            CustomButton,
            0,
            DEF_STYLE_RES
        )
        image = getVectorBitmap(
            context,
            typedArray.getResourceId(R.styleable.CustomButton_srcCompat, 0)
        )
        label = typedArray.getString(R.styleable.CustomButton_label).toString()
        cornerRadius = typedArray.getFloat(R.styleable.CustomButton_radius, 0F)
        backgroundColor = typedArray.getColor(R.styleable.CustomButton_backgroundColor, 0)
        paddings = typedArray.getDimension(R.styleable.CustomButton_paddings, 0F)
        buttonHeight = typedArray.getDimension(R.styleable.CustomButton_height, 0F)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val buttonWidth = resources.displayMetrics.widthPixels -
                convertDpToPixels(context, paddings)
        buttonHeight = convertPixelsToDp(
            context, convertDpToPixels(
                context,
                buttonHeight,
            )
        )
        val initWidth = resolveDefaultSize(widthMeasureSpec, buttonWidth.toInt())
        val initHeight = resolveDefaultSize(heightMeasureSpec, buttonHeight.toInt())
        setMeasuredDimension(initWidth, initHeight)
    }

    private fun resolveDefaultSize(spec: Int, defSize: Int): Int {
        return when (MeasureSpec.getMode(spec)) {
            MeasureSpec.UNSPECIFIED -> MeasureSpec.getSize(defSize)
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(defSize)
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec)
            else -> MeasureSpec.getSize(spec)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        setBackgroundButton()
        canvas?.drawRoundRect(backgroundRect, cornerRadius, cornerRadius, paint)
        val newImage = image?.resizeByWidth(backgroundRect.height().toInt())
        paint.color = Color.BLACK
        paint.textSize = resources.displayMetrics.scaledDensity * 18
        paint.letterSpacing = 0.2F
        newImage?.let { setStartCoordinates(it) }
        newImage?.let { canvas?.drawBitmap(it, startX, 0F, null) }
        val textStartX = newImage?.let { getStartTextX(it) }
        textStartX?.let { canvas?.drawText(label, it, backgroundRect.centerY() + paint.textSize / 2, paint) }
    }

    private fun getStartTextX(img: Bitmap): Float {
        return img.width.toFloat() + startX
    }

    private fun setStartCoordinates(img: Bitmap) {
        startX = (width - paint.measureText(label) - img.width) / 2
    }

    private fun setBackgroundButton() {
        paint.color = Color.WHITE
        backgroundRect.set(0f, 0f, width.toFloat(), buttonHeight)
    }

    private fun convertDpToPixels(context: Context, dp: Float) =
        dp * context.resources.displayMetrics.density

    private fun convertPixelsToDp(context: Context, pixels: Float) =
        pixels / context.resources.displayMetrics.density

    private fun getVectorBitmap(context: Context, drawableId: Int): Bitmap? {
        var bitmap: Bitmap? = null
        when (val drawable = ContextCompat.getDrawable(context, drawableId)) {
            is BitmapDrawable -> {
                bitmap = drawable.bitmap
            }
            is VectorDrawable -> {
                bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
            }
        }
        return bitmap
    }

    private fun Bitmap.resizeByWidth(width: Int): Bitmap {
        val ratio: Float = this.width.toFloat() / this.height.toFloat()
        val height: Int = (width / ratio).roundToInt()
        return Bitmap.createScaledBitmap(
            this,
            width,
            height,
            false
        )
    }
}