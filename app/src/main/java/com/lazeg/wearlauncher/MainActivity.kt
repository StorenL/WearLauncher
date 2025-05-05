package com.lazeg.wearlauncher

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.lazeg.wearlauncher.config.LensConfig
import com.lazeg.wearlauncher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG: String = "MainActivity"
    }

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val lensLayoutManager = LensLayoutManager(lensConfig3)
        mBinding.recyclerView.layoutManager = lensLayoutManager
        mBinding.recyclerView.adapter = IconGridAdapter(getIconList())
        mBinding.recyclerView.setItemViewCacheSize(250)
        LensSnapHelper().attachToRecyclerView(mBinding.recyclerView)
//        LinearSnapHelper().attachToRecyclerView(mBinding.recyclerView)
        mBinding.viewBoard.post {
            Log.d(TAG, "onCreate: ${mBinding.viewBoard.x}, ${mBinding.viewBoard.y}")
        }

        mBinding.btnScrollTo.setOnClickListener {
            mBinding.recyclerView.smoothScrollToPosition(901)
        }
        
        mBinding.slideEffect.addOnChangeListener { slide, value, b ->
            Log.d(TAG, "onCreate: $value, $b")
            lensLayoutManager.updateEffect(value)
        }
    }

    // Example list of icon resources
    private fun getIconList(): List<Int> {
        return mutableListOf<Int>().apply {
            repeat(10000) {
                add(R.drawable.outline_balance_24)
            }
        }
    }

    private val lensConfig4 by lazy {
        LensConfig(180, 180, 5, 5, 1, 100, 100, 0, LensConfig.buildItemConfigs(
            arrayOf(
                floatArrayOf(0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f),
                floatArrayOf(-0.25f, -0.25f, -0.25f, -0.25f, -0.25f, -0.25f, -0.25f),
                floatArrayOf(0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f),
                floatArrayOf(-0.25f, -0.25f, -0.25f, -0.25f, -0.25f, -0.25f, -0.25f),
                floatArrayOf(0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f),
                floatArrayOf(-0.25f, -0.25f, -0.25f, -0.25f, -0.25f, -0.25f, -0.25f),
                floatArrayOf(0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f),
            ),
            arrayOf(
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
            ),
            arrayOf(
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f),
                floatArrayOf(0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f),
                floatArrayOf(0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f),
                floatArrayOf(0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f),
                floatArrayOf(0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
            ),
        ))
    }

    private val lensConfig3 by lazy {
        LensConfig(180, 180, 5, 5, 1, 100, 100, 0, LensConfig.buildItemConfigs(
            arrayOf(
                floatArrayOf(0.5f, 0.5f, 0.5f, 0.0f, -0.5f, -0.5f, -0.5f),
                floatArrayOf(0.5f, 0.4f, 0.1f, 0.0f, -0.1f, -0.4f, -0.5f),
                floatArrayOf(0.5f, 0.1f, 0.0f, 0.0f, 0.0f, -0.1f, -0.5f),
                floatArrayOf(0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.5f),
                floatArrayOf(0.5f, 0.1f, 0.0f, 0.0f, 0.0f, -0.1f, -0.5f),
                floatArrayOf(0.5f, 0.4f, 0.1f, 0.0f, -0.1f, -0.4f, -0.5f),
                floatArrayOf(0.5f, 0.5f, 0.5f, 0.0f, -0.5f, -0.5f, -0.5f),
            ),
            arrayOf(
                floatArrayOf(0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f),
                floatArrayOf(0.5f, 0.4f, 0.1f, 0.0f, 0.1f, 0.4f, 0.5f),
                floatArrayOf(0.5f, 0.1f, 0.0f, 0.0f, 0.0f, 0.1f, 0.5f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(-0.5f, -0.1f, 0.0f, 0.0f, 0.0f, -0.1f, -0.5f),
                floatArrayOf(-0.5f, -0.4f, -0.1f, 0.0f, -0.1f, -0.4f, -0.5f),
                floatArrayOf(-0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f),
            ),
            arrayOf(
                floatArrayOf(-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f),
                floatArrayOf(-1.0f, -0.6f, -0.5f, -0.4f, -0.5f, -0.6f, -1.0f),
                floatArrayOf(-1.0f, -0.5f, -0.2f, -0.1f, -0.2f, -0.5f, -1.0f),
                floatArrayOf(-1.0f, -0.4f, -0.1f, 0.0f, -0.1f, -0.4f, -1.0f),
                floatArrayOf(-1.0f, -0.5f, -0.2f, -0.1f, -0.2f, -0.5f, -1.0f),
                floatArrayOf(-1.0f, -0.6f, -0.5f, -0.4f, -0.5f, -0.6f, -1.0f),
                floatArrayOf(-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f),
            ),
        ))
    }

    private val lensConfig2 by lazy {
        LensConfig(180, 180, 5, 5, 1, 100, 100, 0, LensConfig.buildItemConfigs(
            arrayOf(
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 0.4f, 0.1f, 0.0f, -0.1f, -0.4f, 0.0f),
                floatArrayOf(0.0f, 0.1f, 0.0f, 0.0f, 0.0f, -0.1f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 0.1f, 0.0f, 0.0f, 0.0f, -0.1f, 0.0f),
                floatArrayOf(0.0f, 0.4f, 0.1f, 0.0f, -0.1f, -0.4f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
            ),
            arrayOf(
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 0.4f, 0.1f, 0.0f, 0.1f, 0.4f, 0.0f),
                floatArrayOf(0.0f, 0.1f, 0.0f, 0.0f, 0.0f, 0.1f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, -0.1f, 0.0f, 0.0f, 0.0f, -0.1f, 0.0f),
                floatArrayOf(0.0f, -0.4f, -0.1f, 0.0f, -0.1f, -0.4f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
            ),
            arrayOf(
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 0.4f, 0.5f, 0.6f, 0.5f, 0.4f, 0.0f),
                floatArrayOf(0.0f, 0.5f, 0.8f, 0.9f, 0.8f, 0.5f, 0.0f),
                floatArrayOf(0.0f, 0.6f, 0.9f, 1.0f, 0.9f, 0.6f, 0.0f),
                floatArrayOf(0.0f, 0.5f, 0.8f, 0.9f, 0.8f, 0.5f, 0.0f),
                floatArrayOf(0.0f, 0.4f, 0.5f, 0.6f, 0.5f, 0.4f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
            ),
        ))
    }

    private val lensConfig1 by lazy {
        LensConfig(180, 180, 5, 5, 1, 100, 100, 0, LensConfig.buildItemConfigs(
            arrayOf(
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 0.4f, 0.2f, 0.0f, -0.2f, -0.4f, 0.0f),
                floatArrayOf(0.0f, 0.2f, 0.0f, 0.0f, 0.0f, -0.2f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 0.2f, 0.0f, 0.0f, 0.0f, -0.2f, 0.0f),
                floatArrayOf(0.0f, 0.4f, 0.2f, 0.0f, -0.2f, -0.4f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
            ),
            arrayOf(
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 0.4f, 0.2f, 0.0f, -0.2f, -0.4f, 0.0f),
                floatArrayOf(0.0f, 0.2f, 0.0f, 0.0f, 0.0f, -0.2f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 0.2f, 0.0f, 0.0f, 0.0f, -0.2f, 0.0f),
                floatArrayOf(0.0f, 0.4f, 0.2f, 0.0f, -0.2f, -0.4f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
            ),
//                arrayOf(
//                    floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
//                    floatArrayOf(0.0f, 0.6f, 0.7f, 0.8f, 0.7f, 0.6f, 0.0f),
//                    floatArrayOf(0.0f, 0.7f, 0.8f, 0.9f, 0.8f, 0.7f, 0.0f),
//                    floatArrayOf(0.0f, 0.8f, 0.9f, 1.0f, 0.9f, 0.8f, 0.0f),
//                    floatArrayOf(0.0f, 0.7f, 0.8f, 0.9f, 0.8f, 0.7f, 0.0f),
//                    floatArrayOf(0.0f, 0.6f, 0.7f, 0.8f, 0.7f, 0.6f, 0.0f),
//                    floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
//                ),
            arrayOf(
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                floatArrayOf(0.0f, 0.4f, 0.5f, 0.6f, 0.5f, 0.4f, 0.0f),
                floatArrayOf(0.0f, 0.5f, 0.8f, 0.9f, 0.8f, 0.5f, 0.0f),
                floatArrayOf(0.0f, 0.6f, 0.9f, 1.0f, 0.9f, 0.6f, 0.0f),
                floatArrayOf(0.0f, 0.5f, 0.8f, 0.9f, 0.8f, 0.5f, 0.0f),
                floatArrayOf(0.0f, 0.4f, 0.5f, 0.6f, 0.5f, 0.4f, 0.0f),
                floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
            ),
//                arrayOf(
//                    floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f),
//                    floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f),
//                    floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f),
//                    floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f),
//                    floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f),
//                    floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f),
//                    floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f),
//                ),
        ))
    }
}

// Adapter for the RecyclerView
class IconGridAdapter(private val iconList: List<Int>) :
    RecyclerView.Adapter<IconGridAdapter.IconViewHolder>() {

    class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
        val tvNum: TextView = itemView.findViewById(R.id.tvNum)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.icon_item, parent, false)
        return IconViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        val currentIcon = iconList[position]
        holder.iconImageView.setImageResource(currentIcon)
        holder.tvNum.text = position.toString()
        // You can add click listeners or other logic here if needed
    }

    override fun getItemCount(): Int {
        return iconList.size
    }
}