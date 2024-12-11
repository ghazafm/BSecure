package com.mawar.bsecure.ui.viewModel.sos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mawar.bsecure.model.sos.Sos
import com.mawar.bsecure.repository.SosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SosViewModel(private val repository: SosRepository) : ViewModel() {

    private val _sosList = MutableStateFlow<List<Sos>>(emptyList())
    val sosList: StateFlow<List<Sos>> get() = _sosList

    init {
        fetchAllSos()
    }

    private fun fetchAllSos() {
        viewModelScope.launch {
            repository.getAllSos().collect { sosList ->
                _sosList.value = sosList
            }
        }
    }

    fun addSos(sos: Sos) {
        viewModelScope.launch {
            repository.addSos(sos)
            fetchAllSos() // Refresh list after adding
        }
    }

    fun getSosByLocation(
        negara: String = "",
        provinsi: String = "",
        kota: String = "",
        kecamatan: String = "",
        kelurahan: String = ""
    ) {
        viewModelScope.launch {
            repository.getSosByLocation(negara, provinsi, kota, kecamatan, kelurahan).collect { sosList ->
                _sosList.value = sosList
            }
        }
    }
}