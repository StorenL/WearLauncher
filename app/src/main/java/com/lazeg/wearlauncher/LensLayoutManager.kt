package com.lazeg.wearlauncher

import android.util.Log
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.lazeg.wearlauncher.config.LensConfig
import kotlin.math.max

class LensLayoutManager(val config: LensConfig) : RecyclerView.LayoutManager() {

    companion object {
        private const val DEBUG: Boolean = true
        private const val TAG: String = "LensLayoutManager"
    }

    private var sumDx: Int = 0
    private var sumDy: Int = 0

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
        val startRow = (sumDy + config.cellHeight / 2) / config.cellHeight - config.overDraw
        val startCol = (sumDx + config.cellWidth / 2) / config.cellWidth - config.overDraw
        for (row in startRow until (startRow + config.drawRow + config.overDraw * 2)) {
            for (col in startCol until (startCol + config.drawCol + config.overDraw * 2)) {
                if (row < 0 || col >= config.maxCol || col < 0) {
                    continue
                }
                Log.d(TAG, "fill: $sumDy,$sumDx $startRow,$startCol")
                val index = row * config.maxCol + col
                if (index >= state.itemCount) {
                    continue
                }
                val view = recycler.getViewForPosition(index)
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