package com.mawar.bsecure.ui.viewModel.sos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mawar.bsecure.repository.SosRepository

class SosViewModelFactory(private val repository: SosRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}