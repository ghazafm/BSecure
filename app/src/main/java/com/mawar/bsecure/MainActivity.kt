package com.mawar.bsecure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.mawar.bsecure.repository.SosRepository
import com.mawar.bsecure.ui.SosScreen
import com.mawar.bsecure.ui.viewModel.sos.SosViewModel
import com.mawar.bsecure.ui.viewModel.sos.SosViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = SosRepository()

        val viewModel: SosViewModel by viewModels { SosViewModelFactory(repository) }

        setContent {
            SosScreen(viewModel = viewModel)
        }
    }
}
