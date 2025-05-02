package com.lazeg.wearlauncher

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withClip
import androidx.core.graphics.toColorInt

@Suppress("OVERLOADS_WITHOUT_DEFAULT_ARGUMENTS")
class HoleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val holePath = Path()
    private val viewPath = Path()

    override fun onDraw(canvas: Canvas) {
        holePath.reset()
        // holePath.addCircle(width / 2f, height / 2f, 450f, Path.Direction.CW)
        holePath.addRect(
            width / 2f - 450f,
            height / 2f - 450f,
            width / 2f + 450f,
            height / 2f + 450f,
            Path.Direction.CW
        )
        viewPath.reset()
        viewPath.addRect(0F, 0F, width.toFloat(), height.toFloat(), Path.Direction.CW)
        viewPath.op(holePath, Path.Op.DIFFERENCE)
        canvas.withClip(viewPath) {
            drawColor("#000000".toColorInt())
        }
    }
}