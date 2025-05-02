package com.lazeg.wearlauncher.config

import android.util.Log
import kotlinx.coroutines.sync.Mutex
import java.util.Stack

data class ItemConfig(
    var offsetX: Float,
    var offsetY: Float,
    var scale: Float,
) {
    companion object {
        private const val TAG: String = "ItemConfig"
        internal val DEFAULT = ItemConfig(0F, 0F, 1F)
        private val itemConfigCache: Stack<ItemConfig> = Stack()

        fun obtain(): ItemConfig {
            if (itemConfigCache.isEmpty()) {
                itemConfigCache.push(ItemConfig(0f, 0f, 1f))
            }
            return itemConfigCache.pop()
        }

        fun ItemConfig.recycle() {
            itemConfigCache.push(this)
            Log.d(TAG, "recycle: ${itemConfigCache.size}")
        }

        fun ItemConfig.progressTo(target: ItemConfig, progress: Float): ItemConfig {
            val config = obtain()
            config.offsetX = offsetX + (target.offsetX - offsetX) * progress
            config.offsetY = offsetY + (target.offsetY - offsetY) * progress
            config.scale = scale + (target.scale - scale) * progress
            return config
        }
    }
}

data class LensConfig(
    val cellWidth: Int,
    val cellHeight: Int,
    val drawRow: Int,
    val drawCol: Int,
    val overDraw: Int, // 显示区域外，额外显示的部分
    val maxRow: Int,
    val maxCol: Int,
    val expendDir: Int,
    val itemConfigs: List<List<ItemConfig>>
) {
    fun takeItemConfig(row: Int, col: Int): ItemConfig {
        return if (row < 0 || col < 0 || row >= itemConfigs.size || col >= itemConfigs[row].size) {
            ItemConfig.DEFAULT
        } else {
            itemConfigs[row][col]
        }
    }
}

