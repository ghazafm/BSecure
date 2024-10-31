package com.mawar.bsecure.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun UserScreen(userId: String, viewModel: UserViewModel = hiltViewModel()) {
    val user by viewModel.user.collectAsState()

    LaunchedEffect(userId) {
        viewModel.fetchUserById(userId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        user?.let {
            UserInfo(user = it)
        } ?: Text("Loading...", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun UserInfo(user: com.mawar.bsecure.model.User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "ID: ${user.id}", fontSize = 20.sp)
        Text(text = "Name: ${user.name}", fontSize = 20.sp)
        Text(text = "No Telp: ${user.no_telp}", fontSize = 20.sp)
    }
}
