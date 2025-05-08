package com.lazeg.wearlauncher.config

import android.animation.TypeEvaluator
import android.util.Log
import java.util.Stack

data class ItemConfig(
    var offsetX: Float,
    var offsetY: Float,
    var offsetScale: Float,
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
            config.offsetScale = offsetScale + (target.offsetScale - offsetScale) * progress
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
    companion object {
        fun buildItemConfigs(
            offsetXs: Array<FloatArray>,
            offsetYs: Array<FloatArray>,
            scales: Array<FloatArray>
        ): List<List<ItemConfig>> {
            val itemConfigs: MutableList<List<ItemConfig>> = mutableListOf()
            for (i in 0 until scales.size) {
                val configs: MutableList<ItemConfig> = mutableListOf()
                itemConfigs.add(configs)
                for (j in 0 until offsetXs[i].size) {
                    configs.add(ItemConfig(offsetXs[i][j], offsetYs[i][j], scales[i][j]))
                }
            }
            return itemConfigs
        }
    }

    fun takeItemConfig(row: Int, col: Int): ItemConfig {
        return if (row < 0 || col < 0 || row >= itemConfigs.size || col >= itemConfigs[row].size) {
            ItemConfig.DEFAULT
        } else {
            itemConfigs[row][col]
        }
    }
}

data class OffsetProp<T>(
    val propName: String,
    val defaultValue: T,
    val offsetValue: T,
    val evaluator: TypeEvaluator<T>
)

