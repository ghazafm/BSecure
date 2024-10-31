package com.mawar.bsecure.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mawar.bsecure.model.FirestoreRepository
import com.mawar.bsecure.model.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel : ViewModel() {
    private val repository = FirestoreRepository()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun fetchUserById(userId: String) {
        viewModelScope.launch {
            _user.value = repository.getUserById(userId)
        }
    }
}
