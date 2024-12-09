package com.mawar.bsecure.ui.view.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mawar.bsecure.R

@Composable
fun KebijakanScreen(navController: NavHostController) {
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
            TopBar(title = "Kebijakan Privasi",navController)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Privasi Anda sangat penting bagi kami. Di Be Secure, kami berkomitmen untuk menghormati privasi " +
                        "Anda terkait setiap informasi yang kami kumpulkan melalui aplikasi kami, serta layanan dan situs " +
                        "lain yang kami miliki dan operasikan. Kami ingin memastikan bahwa informasi pribadi Anda diproses dengan transparan dan aman.",
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle(title = "Pengumpulan dan Penggunaan Data")
            SectionContent(
                text = "Kami hanya meminta informasi pribadi jika diperlukan untuk menyediakan layanan kepada Anda, " +
                        "seperti data lokasi untuk melaporkan kejadian secara akurat atau informasi kontak untuk tindak " +
                        "lanjut laporan. Informasi ini dikumpulkan dengan cara yang adil dan sah, dengan sepengetahuan dan persetujuan Anda."
            )

            SectionTitle(title = "Penyimpanan dan Keamanan Data")
            SectionContent(
                text = "Kami menyimpan informasi yang dikumpulkan hanya selama diperlukan untuk memenuhi kewajiban yang " +
                        "diminta atau untuk memenuhi kewajiban hukum. Semua komunikasi dalam aplikasi juga dilindungi oleh enkripsi end-to-end."
            )

            SectionTitle(title = "Berbagi Data dengan Pihak Ketiga")
            SectionContent(
                text = "Kami tidak membagikan informasi pribadi Anda kepada pihak ketiga kecuali jika diwajibkan oleh hukum atau atas persetujuan Anda."
            )
        }
    }
}

@Composable
fun TopBar(title: String,navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {navController.popBackStack()}) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.weight(0.65f))

        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF5A2D82),
        modifier = Modifier.padding(top = 16.dp)
    )
}

@Composable
fun SectionContent(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = Color.Black,
        modifier = Modifier.padding(top = 8.dp)
    )
}