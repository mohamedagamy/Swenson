package com.example.converterlib.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RateItem(val flag: String, val symbol: String, val amount: Double) : Parcelable
