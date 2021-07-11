package com.example.converterlib.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.converterlib.R
import com.example.converterlib.data.model.RateItem
import com.example.converterlib.databinding.ListRateItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class CurrencyAdapter(items: List<RateItem>) :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder?>() {

    private val mValues: List<RateItem>
    var clickListener: ((itemView: View, rateItem: RateItem?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListRateItemBinding.inflate(LayoutInflater.from(parent.getContext()),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageRes = holder.mContext.getResources().getIdentifier(mValues[position].flag,
            "drawable", holder.mContext.getPackageName())
        holder.apply {
            mItem = mValues[position]
            mItemSymbol.setText(mValues[position].symbol)
            mItemRate.setText(mValues[position].amount.toString())
            mItemFlag.setImageResource(if (imageRes != 0) imageRes else R.drawable.flag_eur)
            itemView.setOnClickListener {
                clickListener?.invoke(itemView, mItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(binding: ListRateItemBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        val mItemSymbol: AppCompatTextView
        val mItemRate: AppCompatTextView
        val mItemFlag: AppCompatImageView
        var mItem: RateItem? = null
        val mContext: Context = this.itemView.context
        override fun toString(): String {
            return super.toString() + " '" + mItemSymbol.getText() + "'"
        }

        init {
            mItemSymbol = binding.itemSymbol
            mItemRate = binding.itemRate
            mItemFlag = binding.itemFlag
        }
    }

    init {
        mValues = items
    }
}