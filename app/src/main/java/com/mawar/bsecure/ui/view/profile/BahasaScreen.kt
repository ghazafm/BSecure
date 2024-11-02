package com.mawar.bsecure.ui.view.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mawar.bsecure.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BahasaScreen() {
    var selectedLanguage by remember { mutableStateOf("Indonesia") }
    var searchQuery by remember { mutableStateOf("") }

    // Filter function to determine if the language matches the search query
    fun isLanguageVisible(language: String): Boolean {
        return language.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF5A2D82), shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp))
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Handle back button click */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(0.65f))

                Text(
                    text = "Bahasa",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pilih Bahasa",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Cari Bahasa...") },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24), // Replace with search icon resource
                    contentDescription = "Search",
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLanguageVisible("English")) {
                LanguageOption("English", R.drawable.inggris, selectedLanguage == "English") { selectedLanguage = "English" }
            }
            if (isLanguageVisible("Japanese")) {
                LanguageOption("Japanese", R.drawable.jepang, selectedLanguage == "Japanese") { selectedLanguage = "Japanese" }
            }
            if (isLanguageVisible("Indonesia")) {
                LanguageOption("Indonesia", R.drawable.indonesia, selectedLanguage == "Indonesia") { selectedLanguage = "Indonesia" }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        if (selectedLanguage != "Indonesia") {
            Button(
                onClick = { /* Handle select language action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A2D82)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp)
            ) {
                Text(text = "Pilih Bahasa", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun LanguageOption(name: String, flagRes: Int, isSelected: Boolean, onSelect: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = flagRes),
            contentDescription = name,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = name,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.weight(1f))

        if (isSelected) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_check_24),
                contentDescription = "Selected",
                tint = Color(0xFF5A2D82)
            )
        }
    }
    HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
}

