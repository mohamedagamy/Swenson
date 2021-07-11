package com.example.converterlib.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.converterlib.data.model.RateItem
import com.example.converterlib.databinding.FragmentRateConverterBinding
import timber.log.Timber

class RateConverterFragment : Fragment() {

    private val ARG_PARAM1 = "ITEM"

    private var rateItem: RateItem? = null

    private var _binding: FragmentRateConverterBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRateConverterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            rateItem = it.getParcelable(ARG_PARAM1)
            Timber.d(rateItem.toString())

            rateItem?.let {
                binding?.etBaseCurrency?.setText("1")
                binding?.tvBaseCurrency?.text = "EUR"

                binding?.etTargetCurrency?.setText(rateItem?.amount.toString())
                binding?.tvTargetCurrency?.text = rateItem?.symbol.toString()

                binding?.etBaseCurrency?.doAfterTextChanged {
                    if (it.toString().isNotEmpty())
                        binding?.etTargetCurrency?.setText(((rateItem?.amount
                            ?: 0.0) * it.toString().toDouble()).toString())
                }
            }

        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RateConverterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}