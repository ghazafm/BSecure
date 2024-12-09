package com.mawar.bsecure.ui.view.fakecall

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mawar.bsecure.R
import com.mawar.bsecure.ui.BottomNavItem
import com.mawar.bsecure.ui.viewModel.fakecall.TimerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(navController: NavController, timerViewModel: TimerViewModel) {
    var fromText by remember { mutableStateOf(TextFieldValue("")) }
    var selectedHours by remember { mutableStateOf(0) }
    var selectedMinutes by remember { mutableStateOf(0) }
    var selectedSeconds by remember { mutableStateOf(0) }
    val remainingTime by timerViewModel.remainingTime.collectAsState()
    val navigateToIncomingCall by timerViewModel.navigateToIncomingCall.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(navigateToIncomingCall) {
        if (navigateToIncomingCall && fromText.text.isNotBlank()) {
            timerViewModel.resetNavigation()
            navController.navigate("incoming_call/${fromText.text}")
        }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                containerColor = Color(0xFF4B006E)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomNavItem(iconRes = R.drawable.google, label = "Komunitas")
                    BottomNavItem(iconRes = R.drawable.google, label = "Panggilan Palsu")
                    Spacer(modifier = Modifier.width(56.dp))
                    BottomNavItem(iconRes = R.drawable.google, label = "I'm Here")
                    BottomNavItem(iconRes = R.drawable.google, label = "Profil")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle SOS action */ },
                containerColor = Color.Red,
                shape = CircleShape,
                modifier = Modifier.size(70.dp)
            ) {
                Text(text = "SOS", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFF41275D)),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "From :",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    TextField(
                        value = fromText,
                        onValueChange = { fromText = it },
                        placeholder = { Text("Type Here", color = Color.Gray) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                            cursorColor = Color.White
                        ),
                        textStyle = LocalTextStyle.current.copy(color = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "Set Timer :",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.align(Alignment.Start)
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        TimePickerColumn(
                            range = 0..23,
                            selectedValue = selectedHours,
                            onValueChange = { selectedHours = it },
                            label = "Hours"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(":", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        TimePickerColumn(
                            range = 0..59,
                            selectedValue = selectedMinutes,
                            onValueChange = { selectedMinutes = it },
                            label = "Minutes"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(":", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        TimePickerColumn(
                            range = 0..59,
                            selectedValue = selectedSeconds,
                            onValueChange = { selectedSeconds = it },
                            label = "Seconds"
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Preset Timer Buttons
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        PresetTimerButton(label = "00:05", buttonColor = Color(0x33D9D9D9)) {
                            timerViewModel.startTimer(5)
                        }
                        PresetTimerButton(label = "00:10", buttonColor = Color(0x33D9D9D9)) {
                            timerViewModel.startTimer(10)
                        }
                        PresetTimerButton(label = "00:15", buttonColor = Color(0x33D9D9D9)) {
                            timerViewModel.startTimer(15)
                        }
                    }

                    // Display Remaining Time
                    if (remainingTime > 0) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Waktu tersisa: $remainingTime detik",
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Move Start Button to the Bottom
                    Spacer(modifier = Modifier.height(80.dp)) // Add extra space above Start Button
                    Button(
                        onClick = {
                            val duration = selectedHours * 3600 + selectedMinutes * 60 + selectedSeconds
                            if (duration > 0 && fromText.text.isNotBlank()) {
                                timerViewModel.startTimer(duration)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA5DD9B)),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .width(120.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Start", color = Color.White, fontSize = 30.sp)
                    }
                }
            }
        }
    )
}

@Composable
fun TimePickerColumn(
    range: IntRange,
    selectedValue: Int,
    onValueChange: (Int) -> Unit,
    label: String
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(selectedValue) {
        coroutineScope.launch {
            listState.animateScrollToItem(selectedValue)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(text = label, color = Color(0xFFD9D9D9), fontSize = 14.sp, fontWeight = FontWeight.Medium)

        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.height(200.dp)
        ) {
            itemsIndexed(range.toList()) { _, value ->
                val isSelected = value == selectedValue
                Text(
                    text = String.format("%02d", value),
                    color = if (isSelected) Color.White else Color.Gray,
                    fontSize = if (isSelected) 60.sp else 36.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier
                        .padding(vertical = if (isSelected) 6.dp else 4.dp)
                        .clickable { onValueChange(value) }
                )
            }
        }
    }
}

@Composable
fun PresetTimerButton(label: String, buttonColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        shape = CircleShape,
        modifier = Modifier.size(120.dp)
    ) {
        Text(text = label, color = Color.White, fontSize = 20.sp)
    }
}
