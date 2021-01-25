package com.somanath.example.dailypriceapidemo.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.somanath.example.dailypriceapidemo.databinding.PricingDeatilsLoadstateFooterItemBinding

class PricingDetailsLoadStateAdapter(private val retry: () -> Unit) :
        LoadStateAdapter<PricingDetailsLoadStateAdapter.LoadStateViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = PricingDeatilsLoadstateFooterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: PricingDeatilsLoadstateFooterItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
                textViewError.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }

}