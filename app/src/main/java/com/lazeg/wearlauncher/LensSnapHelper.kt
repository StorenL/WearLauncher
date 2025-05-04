package com.lazeg.wearlauncher

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

class LensSnapHelper : SnapHelper() {
    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        if (layoutManager !is LensLayoutManager) {
            return intArrayOf(0, 0)
        }
        var offsetX = layoutManager.sumDx % layoutManager.config.cellWidth
        offsetX = if (offsetX > layoutManager.config.cellWidth / 2) {
            layoutManager.config.cellWidth - offsetX
        } else {
            -offsetX
        }
        var offsetY = layoutManager.sumDy % layoutManager.config.cellHeight
        offsetY = if (offsetY > layoutManager.config.cellHeight / 2) {
            layoutManager.config.cellHeight - offsetY
        } else {
            -offsetY
        }
        return intArrayOf(offsetX, offsetY)
    }

    /**
     * 无需通过View来计算，直接通过sumDx和sumDy计算
     * 返回一个非Null的View，确保 calculateDistanceToFinalSnap 正常执行
     *
     * @see SnapHelper.calculateDistanceToFinalSnap
     */
    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        if (layoutManager !is LensLayoutManager) {
            return null
        }
        if (layoutManager.childCount == 0) {
            return null
        }
        return layoutManager.getChildAt(0)
    }

    /**
     * For Fling. Find the target position to snap to.
     */
    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider) {
            return RecyclerView.NO_POSITION
        }
        if (layoutManager !is LensLayoutManager) {
            return RecyclerView.NO_POSITION
        }
        val itemCount = layoutManager.itemCount
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION
        }
        val anchorView = findSnapView(layoutManager)
        if (anchorView == null) {
            return RecyclerView.NO_POSITION
        }
        val anchorPosition = layoutManager.getPosition(anchorView)
        if (anchorPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION
        }
        val vectorForEnd = layoutManager.computeScrollVectorForPosition(itemCount - 1)
        if (vectorForEnd == null) {
            return RecyclerView.NO_POSITION
        }
        var hDeltaJump = 0
        var vDeltaJump = 0
        val distance = calculateScrollDistance(velocityX, velocityY)
        hDeltaJump = distance[0] / layoutManager.config.cellWidth
        if (vectorForEnd.x < 0) {
            hDeltaJump = -hDeltaJump
        }
        vDeltaJump = distance[1] / layoutManager.config.cellHeight
        if (vectorForEnd.y < 0) {
            vDeltaJump = -vDeltaJump
        }
        if (hDeltaJump == 0 || vDeltaJump == 0) {
            return RecyclerView.NO_POSITION
        }
        var targetPos = anchorPosition + vDeltaJump * layoutManager.config.maxCol + hDeltaJump
        if (targetPos >= itemCount) {
            targetPos = itemCount - 1
        }
        if (targetPos < 0) {
            targetPos = 0
        }
        return targetPos
    }
}