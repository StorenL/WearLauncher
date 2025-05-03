package com.lazeg.wearlauncher

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        mBinding.recyclerView.layoutManager = LensLayoutManager(
//            LensConfig(300, 300, 3, 3, 1, 100, 100, 0, listOf(
//                listOf(ItemConfig(0F, 0F, 1F), ItemConfig(0F, 0F, 1F), ItemConfig(0F, 0F, 1F), ItemConfig(0F, 0F, 1F), ItemConfig(0F, 0F, 1F)),
//                listOf(ItemConfig(0F, 0F, 1F), ItemConfig(0F, 0F, 0.5F), ItemConfig(0F, 0F, 1F), ItemConfig(0F, 0F, 0.5F), ItemConfig(0F, 0F, 1F)),
//                listOf(ItemConfig(0F, 0F, 1F), ItemConfig(0F, 0F, 1F), ItemConfig(0F, 0F, 0.5F), ItemConfig(0F, 0F, 1F), ItemConfig(0F, 0F, 1F)),
//                listOf(ItemConfig(0F, 0F, 1F), ItemConfig(0F, 0F, 0.5F), ItemConfig(0F, 0F, 1F), ItemConfig(0F, 0F, 0.5F), ItemConfig(0F, 0F, 1F)),
//                listOf(ItemConfig(0F, 0F, 1F), ItemConfig(0F, 0F, 1F), ItemConfig(0F, 0F, 1F), ItemConfig(0F, 0F, 1F), ItemConfig(0F, 0F, 1F)),
//            ))
            LensConfig(180, 180, 5, 5, 1, 100, 100, 0, LensConfig.buildItemConfigs(
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
                    floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                    floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                    floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                    floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                    floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                    floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                ),
                arrayOf(
                    floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                    floatArrayOf(0.0f, 0.9f, 1.0f, 1.1f, 1.0f, 0.9f, 0.0f),
                    floatArrayOf(0.0f, 1.0f, 1.1f, 1.2f, 1.1f, 1.0f, 0.0f),
                    floatArrayOf(0.0f, 1.1f, 1.2f, 1.5f, 1.2f, 1.1f, 0.0f),
                    floatArrayOf(0.0f, 1.0f, 1.1f, 1.2f, 1.1f, 1.0f, 0.0f),
                    floatArrayOf(0.0f, 0.9f, 1.0f, 1.1f, 1.0f, 0.9f, 0.0f),
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
        )
        mBinding.recyclerView.adapter = IconGridAdapter(getIconList())
        // LinearSnapHelper().attachToRecyclerView(mBinding.recyclerView)
        mBinding.viewBoard.post {
            Log.d(TAG, "onCreate: ${mBinding.viewBoard.x}, ${mBinding.viewBoard.y}")
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
}

// Adapter for the RecyclerView
class IconGridAdapter(private val iconList: List<Int>) :
    RecyclerView.Adapter<IconGridAdapter.IconViewHolder>() {

    class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.icon_item, parent, false)
        return IconViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        val currentIcon = iconList[position]
        holder.iconImageView.setImageResource(currentIcon)
        // You can add click listeners or other logic here if needed
    }

    override fun getItemCount(): Int {
        return iconList.size
    }
}