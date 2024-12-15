package com.mawar.bsecure.ui.viewModel.sos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mawar.bsecure.model.sos.Sos
import com.mawar.bsecure.repository.SosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SosViewModel(private val repository: SosRepository) : ViewModel() {

    private val _sosList = MutableStateFlow<List<Sos>>(emptyList())
    val sosList: StateFlow<List<Sos>> get() = _sosList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        fetchEachSos()
    }

    private fun fetchAllSos() {
        viewModelScope.launch {
            repository.getAllSos().collect { sosList ->
                _sosList.value = sosList
            }
        }
    }
    private fun fetchEachSos() {
        viewModelScope.launch {
            repository.getEach().collect { sosList ->
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
            try {
                _isLoading.value = true
                Log.d(
                    "SosViewModel",
                    "Querying Firestore with: negara=$negara, provinsi=$provinsi, kota=$kota, kecamatan=$kecamatan, kelurahan=$kelurahan"
                )
                repository.getSosByLocation(negara, provinsi, kota, kecamatan, kelurahan)
                    .collectLatest { sosList ->
                        Log.d("SosViewModel", "Fetched: ${sosList.size}")
                        val updatedList = sosList.toList() // force copy to trigger StateFlow change
                        if (_sosList.value.toSet() != updatedList.toSet()) {
                            _sosList.value = updatedList
                            Log.d("SosViewModel", "Updated SOS List: ${updatedList.size} items")
                        } else {
                            Log.d("SosViewModel", "No change in SOS list, skipping update")
                        }
                    }
            } catch (e: Exception) {
                Log.e("SosViewModel", "Error fetching SOS by location", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}