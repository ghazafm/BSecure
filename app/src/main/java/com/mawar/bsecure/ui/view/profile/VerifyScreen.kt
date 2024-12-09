package com.mawar.bsecure.ui.view.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mawar.bsecure.R

@Preview
@Composable
fun VerifyScreen() {
    var otp1 by remember { mutableStateOf(TextFieldValue("")) }
    var otp2 by remember { mutableStateOf(TextFieldValue("")) }
    var otp3 by remember { mutableStateOf(TextFieldValue("")) }
    var otp4 by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5A2D82)) // Background color for the top part
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { /* Handle back button click */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.back1),
                    contentDescription = "Back",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.width(80.dp))

            Text(
                text = "Verifikasi",
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Image(
                painter = painterResource(id = R.drawable.bsecure),
                contentDescription = "Bsecure",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(60.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Divider(color = Color.Gray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(id = R.drawable.verify), // Replace with your lock image resource
                contentDescription = "Lock Icon",
                modifier = Modifier.size(225.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Masukkan OTP",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5A2D82),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Empat digit kode OTP sudah dikirim ke nomormu!",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "MulMul@gmail.com",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                OTPTextField(value = otp1, onValueChange = { otp1 = it })
                OTPTextField(value = otp2, onValueChange = { otp2 = it })
                OTPTextField(value = otp3, onValueChange = { otp3 = it })
                OTPTextField(value = otp4, onValueChange = { otp4 = it })
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { /* Handle verify button click */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A2D82)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Verifikasi", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Kirim ulang OTP",
                fontSize = 14.sp,
                color = Color(0xFF5A2D82),
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable { /* Handle resend OTP click */ }
            )
        }
    }
}

@Composable
fun OTPTextField(value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .size(60.dp)
            .padding(horizontal = 4.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        ),
        singleLine = true,
        visualTransformation = VisualTransformation.None
    )
}
