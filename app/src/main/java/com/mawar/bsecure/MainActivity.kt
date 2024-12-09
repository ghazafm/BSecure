package com.mawar.bsecure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mawar.bsecure.ui.view.fakecall.TimerScreen
import com.mawar.bsecure.ui.viewModel.fakecall.TimerViewModel
import com.mawar.bsecure.ui.view.fakecall.IncomingCallScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val timerViewModel: TimerViewModel = viewModel()

    NavHost(navController = navController, startDestination = "timer_screen") {
        composable("timer_screen") {
            TimerScreen(navController = navController, timerViewModel = timerViewModel)
        }
        composable("incoming_call/{contactName}") { backStackEntry ->
            val contactName = backStackEntry.arguments?.getString("contactName") ?: "Nama Tidak Diketahui"
            IncomingCallScreen(contactName = contactName)
        }
    }
}
