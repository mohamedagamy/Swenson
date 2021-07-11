package com.example.converterlib.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.converterlib.data.model.Resource
import com.example.converterlib.data.repo.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    fun getLatest() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val result = appRepository.getLatest()
            emit(Resource.success(data = result))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
