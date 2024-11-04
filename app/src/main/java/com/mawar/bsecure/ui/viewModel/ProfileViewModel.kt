package com.mawar.bsecure.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mawar.bsecure.repository.UserRepository
import com.mawar.bsecure.model.AppUser // Change this import
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userData = MutableStateFlow<AppUser?>(null) // Change type
    val userData = _userData.asStateFlow()

    fun fetchUserData(uid: String) {
        viewModelScope.launch {
            val user = userRepository.getUserData(uid)
            _userData.value = user
        }
    }
}
