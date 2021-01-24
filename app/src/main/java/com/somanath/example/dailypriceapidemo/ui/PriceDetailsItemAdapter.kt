package com.somanath.example.dailypriceapidemo.ui

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.somanath.example.dailypriceapidemo.R
import com.somanath.example.dailypriceapidemo.data.Record
import com.somanath.example.dailypriceapidemo.databinding.CommodityDetailItemViewBinding

class PriceDetailsItemAdapter():
    PagingDataAdapter<Record,PriceDetailsItemAdapter.DetailItemViewHolder>(ITEM_COMPARATOR) {

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Record>() {
            override fun areItemsTheSame(oldItem: Record, newItem: Record) =
                    oldItem.variety == newItem.variety

            override fun areContentsTheSame(oldItem: Record, newItem: Record) =
                    oldItem == newItem
        }
    }

    private lateinit var resources: Resources

    inner class DetailItemViewHolder(private val binding: CommodityDetailItemViewBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(item:Record){
            binding.apply {
                commodityNameTitle.text = resources.getString(R.string.commodity_name,item.commodity)
                state.text = resources.getString(R.string.state, item.state)
                district.text = resources.getString(R.string.district,item.district)
                market.text =  resources.getString(R.string.market,item.market)
                minPrice.text = resources.getString(R.string.min_price,item.minPrice)
                maxPrice.text = resources.getString(R.string.max_price,item.maxPrice)
                modalPrice.text  = resources.getString(R.string.modal_price,item.modalPrice)
                date.text = resources.getString(R.string.market,item.arrivalDate)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailItemViewHolder {
        resources = parent.context.resources
        val binding = CommodityDetailItemViewBinding.inflate(
            LayoutInflater.from(parent.context) ,parent,false)
        return DetailItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailItemViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

}