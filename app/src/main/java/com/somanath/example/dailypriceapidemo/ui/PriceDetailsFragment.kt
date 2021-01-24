package com.somanath.example.dailypriceapidemo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.somanath.example.dailypriceapidemo.R
import com.somanath.example.dailypriceapidemo.databinding.FragmentPriceDetailsBinding
import com.somanath.example.dailypriceapidemo.paging.PricingDetailsLoadStateAdapter
import com.somanath.example.dailypriceapidemo.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PriceDetailsFragment: Fragment(R.layout.fragment_price_details) {
    private lateinit var binding : FragmentPriceDetailsBinding
    private val viewModel  by  viewModels<PriceDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPriceDetailsBinding.bind(view)
        val adapter = PriceDetailsItemAdapter()
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = PricingDetailsLoadStateAdapter { adapter.retry() },
                    footer = PricingDetailsLoadStateAdapter { adapter.retry() },
            )
        }

        viewModel.items.observe(viewLifecycleOwner){
            it?.let { it1 -> adapter.submitData(viewLifecycleOwner.lifecycle, it1) }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}