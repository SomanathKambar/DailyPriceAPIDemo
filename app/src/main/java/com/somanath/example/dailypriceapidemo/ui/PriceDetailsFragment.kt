package com.somanath.example.dailypriceapidemo.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
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
    private lateinit var  adapter : PriceDetailsItemAdapter

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding = FragmentPriceDetailsBinding.bind(view)
        adapter = PriceDetailsItemAdapter()
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

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }

     override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            inflater.inflate(R.menu.main_menu, menu)
         super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.filter) {
            showFilterPopup()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showFilterPopup() {
        val states = arrayOf("Karnataka", "Maharashtra", "Gujarath", "Chattisgarh")

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Pick a State")
        builder.setItems(states, DialogInterface.OnClickListener { dialog, which ->
            viewModel.filteredData.value = which.toString()
            dialog.dismiss()
        })
        builder.show()

    }
}