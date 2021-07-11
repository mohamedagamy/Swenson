package com.example.converterlib.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.converterlib.R
import com.example.converterlib.data.model.ApiResult
import com.example.converterlib.data.model.RateItem
import com.example.converterlib.data.model.Status
import com.example.converterlib.databinding.FragmentRateListBinding
import com.example.converterlib.network.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RateListFragment : Fragment() {
    private var _binding: FragmentRateListBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRateListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    private fun initViews() {
        getLatestRates()
        initSwipeRefresh()
    }

    @SuppressLint("CheckResult")
    private fun getLatestRates() {
        binding?.swipeToRefresh?.isRefreshing = true
        (activity as ConverterActivity).appViewModel.getLatest()
            .observe(viewLifecycleOwner, {
                binding?.swipeToRefresh?.isRefreshing = false
                Timber.d("status: ${it.status}")
                when (it.status) {
                    Status.SUCCESS -> {
                        Timber.d("SUCCESS")
                        Timber.d("data: ${it.data}")
                        it.data?.let {
                            loadResponse(it)
                        }
                    }
                    Status.ERROR -> {
                        Timber.d("ERROR")
                        context?.showToast("server error")
                    }
                    Status.LOADING -> {
                        Timber.d("LOADING")
                        binding?.swipeToRefresh?.isRefreshing = true
                    }
                }

            })
    }

    private fun loadResponse(it: ApiResult) {
        Timber.d(it.toString())
        showEmptyHolder(it.rates.entries.isEmpty())
        val rateItems = mutableListOf<RateItem>()
        rateItems.addAll(it.rates.entries.map {
            RateItem("flag_" + it.key.lowercase(),
                it.key,
                it.value)
        })
        val currencyAdapter = CurrencyAdapter(rateItems)
        binding?.rvNewsList?.adapter = currencyAdapter

        //handle item click
        currencyAdapter.clickListener = { itemView, item ->
            val bundle = bundleOf("ITEM" to item)
            itemView.findNavController()
                .navigate(R.id.action_nav_rateListFrg_to_rateConverterFrg, bundle)
        }
    }

    private fun showEmptyHolder(isShown: Boolean) {
        if (isShown) {
            binding?.rvNewsList?.visibility = View.GONE
            binding?.ivPlaceholder?.visibility = View.VISIBLE
        } else {
            binding?.rvNewsList?.visibility = View.VISIBLE
            binding?.ivPlaceholder?.visibility = View.GONE
        }
    }

    private fun initSwipeRefresh() {
        binding?.swipeToRefresh?.setOnRefreshListener {
            Handler().postDelayed(Runnable { // Stop animation (This will be after 3 seconds)
                binding?.swipeToRefresh?.isRefreshing = false
            }, 200) // Delay in millis
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}