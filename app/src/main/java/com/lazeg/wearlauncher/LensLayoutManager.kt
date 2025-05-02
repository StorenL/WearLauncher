package com.lazeg.wearlauncher

import android.util.Log
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.lazeg.wearlauncher.config.ItemConfig.Companion.progressTo
import com.lazeg.wearlauncher.config.ItemConfig.Companion.recycle
import com.lazeg.wearlauncher.config.LensConfig
import kotlin.math.max
import kotlin.math.roundToInt

class LensLayoutManager(val config: LensConfig) : RecyclerView.LayoutManager() {

    companion object {
        private const val DEBUG: Boolean = true
        private const val TAG: String = "LensLayoutManager"
    }

    private var sumDx: Int = 0
    private var sumDy: Int = 0
    private var horProgress: Float = 0F
    private var verProgress: Float = 0F

    private val horHelper = OrientationHelper.createOrientationHelper(this, RecyclerView.HORIZONTAL)
    private val verHelper = OrientationHelper.createOrientationHelper(this, RecyclerView.VERTICAL)

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)

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
//        val startRow = (sumDy + config.cellHeight / 2) / config.cellHeight - config.overDraw
//        val startCol = (sumDx + config.cellWidth / 2) / config.cellWidth - config.overDraw

        val rowProgress = sumDy * 1f / config.cellHeight
        val startRow = rowProgress.toInt() - config.overDraw
        val colProgress = sumDx * 1f / config.cellWidth
        val startCol = colProgress.toInt() - config.overDraw

        for (row in startRow until (startRow + config.drawRow + config.overDraw * 2)) {
            for (col in startCol until (startCol + config.drawCol + config.overDraw * 2)) {
                if (row < 0 || col >= config.maxCol || col < 0) {
                    continue
                }
                val index = row * config.maxCol + col
                if (index >= state.itemCount) {
                    continue
                }
                val view = recycler.getViewForPosition(index)

                var curRowCurColItemConfig = config.takeItemConfig(row - startRow, col - startCol)
                var preRowCurColItemConfig = config.takeItemConfig(row - startRow - 1, col - startCol)
                curRowCurColItemConfig = curRowCurColItemConfig.progressTo(preRowCurColItemConfig, rowProgress - rowProgress.toInt())

                var curRowPreColItemConfig = config.takeItemConfig(row - startRow, col - startCol - 1)
                val preRowPreColItemConfig =  config.takeItemConfig(row - startRow - 1, col - startCol - 1)
                curRowPreColItemConfig = curRowPreColItemConfig.progressTo(preRowPreColItemConfig, rowProgress - rowProgress.toInt())

                val itemConfig = curRowCurColItemConfig.progressTo(curRowPreColItemConfig, colProgress - colProgress.toInt())

                curRowCurColItemConfig.recycle()
                curRowPreColItemConfig.recycle()
                itemConfig.recycle()

                if (row == 1 && col == 0) {
                    Log.d(TAG, "fill: $sumDy,$sumDx $startRow,$startCol $rowProgress\n$colProgress\n$itemConfig")
                }
                view.scaleX = itemConfig.scale
                view.scaleY = itemConfig.scale
                addView(view)
                measureChildWithMargins(view, 0, 0)
                val left = col * config.cellWidth - sumDx + paddingLeft
                val top = row * config.cellHeight - sumDy + paddingTop
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
        sumDy += dy
        Log.d(TAG, "scrollVerticallyBy: $sumDx, $sumDy")
        detachAndScrapAttachedViews(recycler)
        fill(recycler, state)
        // verHelper.offsetChildren(-dy)
        return dy
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        sumDx += dx
        detachAndScrapAttachedViews(recycler)
        fill(recycler, state)
        // horHelper.offsetChildren(-dx)
        return dx
    }

    override fun canScrollVertically(): Boolean = true

    override fun canScrollHorizontally(): Boolean = true
}