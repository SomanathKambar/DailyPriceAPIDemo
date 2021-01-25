package com.somanath.example.dailypriceapidemo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.somanath.example.dailypriceapidemo.R
import com.somanath.example.dailypriceapidemo.databinding.FragmentPriceDetailsBinding
import com.somanath.example.dailypriceapidemo.paging.PricingDetailsLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class PriceDetailsFragment : Fragment(R.layout.fragment_price_details) {
    private lateinit var binding: FragmentPriceDetailsBinding
    private val viewModel by viewModels<PriceDetailsViewModel>()
    private val disposable = CompositeDisposable()

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

        disposable.add(viewModel.getPriceDetailsItems().subscribe {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}