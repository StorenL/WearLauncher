package com.lazeg.wearlauncher

import android.util.Log
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.lazeg.wearlauncher.config.ItemConfig.Companion.progressTo
import com.lazeg.wearlauncher.config.ItemConfig.Companion.recycle
import com.lazeg.wearlauncher.config.LensConfig
import kotlin.math.roundToInt

class LensLayoutManager(internal val config: LensConfig) :
    RecyclerView.LayoutManager()
    /*RecyclerView.SmoothScroller.ScrollVectorProvider*/ {

    companion object {
        private const val DEBUG: Boolean = true
        private const val TAG: String = "LensLayoutManager"
    }

    internal var sumDx: Int = 0
    internal var sumDy: Int = 0

    private var effectFactor: Float = 1f

    fun updateEffect(factor: Float) {
        effectFactor = factor
        requestLayout()
    }

    private val horHelper = OrientationHelper.createOrientationHelper(this, RecyclerView.HORIZONTAL)
    private val verHelper = OrientationHelper.createOrientationHelper(this, RecyclerView.VERTICAL)

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
//        RecyclerView.LayoutParams(
//            config.cellWidth,
//            config.cellHeight
//        )
        RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (state.itemCount == 0) {
            removeAndRecycleAllViews(recycler)
            return
        }
        detachAndScrapAttachedViews(recycler)
        fill(recycler, state)
    }

    private fun fill(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        // 为了确保显示区域尽量居中（上滑1.2个格子时，还是从0开始绘制；上滑1.8个格子时，从1开始绘制）
        // val startRow = (sumDy + config.cellHeight / 2) / config.cellHeight - config.overDraw
        // val startCol = (sumDx + config.cellWidth / 2) / config.cellWidth - config.overDraw

        val rowProgress = sumDy * 1f / config.cellHeight
        val colProgress = sumDx * 1f / config.cellWidth
        val startRowRound = rowProgress.roundToInt() - config.overDraw
        val startColRound = colProgress.roundToInt() - config.overDraw
        val startRow = rowProgress.toInt() - config.overDraw
        val startCol = colProgress.toInt() - config.overDraw

        for (row in startRowRound until (startRowRound + config.drawRow + config.overDraw * 2)) {
            for (col in startColRound until (startColRound + config.drawCol + config.overDraw * 2)) {
                if (row < 0 || col >= config.maxCol || col < 0) {
                    continue
                }
                val index = row * config.maxCol + col
                if (index >= state.itemCount) {
                    continue
                }
                val view = recycler.getViewForPosition(index)

                var curRowCurColItemConfig = config.takeItemConfig(row - startRow, col - startCol)
                var preRowCurColItemConfig =
                    config.takeItemConfig(row - startRow - 1, col - startCol)
                curRowCurColItemConfig = curRowCurColItemConfig.progressTo(
                    preRowCurColItemConfig,
                    rowProgress - rowProgress.toInt()
                )

                var curRowPreColItemConfig =
                    config.takeItemConfig(row - startRow, col - startCol - 1)
                val preRowPreColItemConfig =
                    config.takeItemConfig(row - startRow - 1, col - startCol - 1)
                curRowPreColItemConfig = curRowPreColItemConfig.progressTo(
                    preRowPreColItemConfig,
                    rowProgress - rowProgress.toInt()
                )

                val itemConfig = curRowCurColItemConfig.progressTo(
                    curRowPreColItemConfig,
                    colProgress - colProgress.toInt()
                )

                curRowCurColItemConfig.recycle()
                curRowPreColItemConfig.recycle()
                itemConfig.recycle()
                view.scaleX = 1 + itemConfig.offsetScale * effectFactor
                view.scaleY = 1 + itemConfig.offsetScale * effectFactor
                view.layoutParams.width = config.cellWidth
                view.layoutParams.height = config.cellHeight
                addView(view)
                measureChildWithMargins(view, 0, 0)
                val left = col * config.cellWidth - sumDx + paddingLeft + // Origin position
                        (itemConfig.offsetX * config.cellWidth * effectFactor).toInt()   // With offset
                val top = row * config.cellHeight - sumDy + paddingTop + // Origin position
                        (itemConfig.offsetY * config.cellHeight * effectFactor).toInt()  // With offset
                layoutDecoratedWithMargins(
                    view,
                    left,
                    top,
                    left + config.cellWidth,
                    top + config.cellHeight
                )
                if (DEBUG) {
                    val color =
                        if ((row + col) % 2 == 0) android.graphics.Color.RED else android.graphics.Color.BLUE
                    view.setBackgroundColor(color)
                }
            }
        }
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        var consume = dy
        sumDy += dy
        if (sumDy < 0) {
            consume = -(sumDy - dy)
            sumDy = 0
        }
        val max = (config.maxCol - config.drawCol) * config.cellHeight
        if (sumDy > max) {
            consume = max - (sumDy - dy)
            sumDy = max
        }
        detachAndScrapAttachedViews(recycler)
        fill(recycler, state)
        // verHelper.offsetChildren(-dy)
        return consume
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        var consume = dx
        sumDx += dx
        if (sumDx < 0) {
            consume = -(sumDx - dx)
            sumDx = 0
        }
        val max = (config.maxRow - config.drawRow) * config.cellWidth
        if (sumDx > max) {
            consume = max - (sumDx - dx)
            sumDx = max
        }
        detachAndScrapAttachedViews(recycler)
        fill(recycler, state)
        // horHelper.offsetChildren(-dx)
        return consume
    }

    override fun canScrollVertically(): Boolean = true

    override fun canScrollHorizontally(): Boolean = true

//    override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
//        if (childCount == 0) {
//            return null
//        }
//        val firstChild = getChildAt(0) ?: return null
//        val firstChildPos = getPosition(firstChild)
//        val firstChildRow = firstChildPos / config.maxCol
//        val firstChildCol = firstChildPos % config.maxCol
//        val targetRow = targetPosition / config.maxCol
//        val targetCol = targetPosition % config.maxCol
//        var directionX = (targetCol - firstChildCol).coerceIn(-1, 1)
//        if (!canScrollHorizontally()) {
//            directionX = 0
//        }
//        var directionY = (targetRow - firstChildRow).coerceIn(-1, 1)
//        if (!canScrollVertically()) {
//            directionY = 0
//        }
//        return PointF(directionX.toFloat(), directionY.toFloat())
//    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        // Not available
        // LinearSmoothScroller(recyclerView.context).let {
        //     it.targetPosition = position
        //     startSmoothScroll(it)
        // }
        Log.d(TAG, "smoothScrollToPosition: $position")
        val targetSumDx = position % config.maxCol * config.cellWidth
        val targetSumDy = position / config.maxCol * config.cellHeight
        recyclerView.smoothScrollBy(targetSumDx - sumDx, targetSumDy - sumDy)
    }
}