package com.mawar.bsecure.ui.viewModel.fakeCall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {

    private val _remainingTime = MutableStateFlow(0)
    val remainingTime: StateFlow<Int> = _remainingTime

    private val _navigateToIncomingCall = MutableStateFlow(false)
    val navigateToIncomingCall: StateFlow<Boolean> = _navigateToIncomingCall

    private var isTimerRunning = false

    fun startTimer(durationInSeconds: Int) {
        if (isTimerRunning) return // Cegah multiple timer

        _remainingTime.value = durationInSeconds
        _navigateToIncomingCall.value = false
        isTimerRunning = true

        viewModelScope.launch {
            while (_remainingTime.value > 0) {
                delay(1000L)
                _remainingTime.value -= 1
            }
            _navigateToIncomingCall.value = true
            isTimerRunning = false // Reset status setelah timer selesai
        }
    }

    fun resetNavigation() {
        _navigateToIncomingCall.value = false
    }
}