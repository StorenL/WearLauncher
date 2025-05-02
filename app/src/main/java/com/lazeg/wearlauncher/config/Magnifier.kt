package com.lazeg.wearlauncher.config

data class ItemConfig(
    val offsetX: Float,
    val offsetY: Float,
    val scale: Float,
)

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
)

