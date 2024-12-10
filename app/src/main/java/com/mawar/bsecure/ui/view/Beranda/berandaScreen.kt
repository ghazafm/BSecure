package com.mawar.wtf

import androidx.compose.foundation.layout.Column
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng
import com.mawar.bsecure.R
import com.mawar.bsecure.data.emergency.EmergencyServiceData
import com.mawar.bsecure.data.emergency.toLatLng
import com.mawar.bsecure.ui.view.Beranda.Bottom
import com.mawar.bsecure.ui.view.Beranda.TopBars
import com.mawar.bsecure.ui.view.Beranda.card
import com.mawar.bsecure.ui.view.Beranda.floatNotif

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold (
                topBar = { TopBars() },
                bottomBar = { Bottom() },


                ){
                    innerpadding ->
                val painter = painterResource(R.drawable.ic_launcher_background)
                val contentDescription = "photo1"
                val title = "photo1"

                val painter1 = painterResource(R.drawable.ic_launcher_background)
                val contentDescription1 = "photo1"
                val title1 = "photo1"
                Row (modifier = Modifier.padding(top = 50.dp)){
                    Box{
                        card(painter = painter,
                            modifier = Modifier.padding(innerpadding),
                            contentDescriptoin = contentDescription,
                            title = title)
                    }

                    Box{
                        card(painter = painter1,
                            modifier = Modifier
                                .padding(innerpadding)
                                .padding(top = 16.dp),
                            contentDescriptoin = contentDescription1,
                            title = title1)
                    }


                }

            }


        }
    }
}

@Preview
@Composable
private fun tampil() {

    Scaffold(
        topBar = { TopBars() },
        bottomBar = { Bottom() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            val painter = painterResource(R.drawable.ic_launcher_background)
            val contentDescription = "photo1"
            val title = "photo1"

            val painter1 = painterResource(R.drawable.ic_launcher_background)
            val contentDescription1 = "photo1"
            val title1 = "photo1"

            Row(modifier = Modifier.padding(top = 0.dp)) {
                Column {
                    Box {
                        card(
                            painter = painter,
                            modifier = Modifier.padding(innerPadding),
                            contentDescriptoin = contentDescription,
                            title = title
                        )
                    }

                    Box {
                        card(
                            painter = painter1,
                            modifier = Modifier
                                .padding(innerPadding)
                                .padding(top = 16.dp),
                            contentDescriptoin = contentDescription1,
                            title = title1
                        )
                    }

                    Box {
                        card(
                            painter = painter1,
                            modifier = Modifier
                                .padding(innerPadding)
                                .padding(top = 16.dp),
                            contentDescriptoin = contentDescription1,
                            title = title1
                        )
                    }
                    Box {
                        card(
                            painter = painter1,
                            modifier = Modifier
                                .padding(innerPadding)
                                .padding(top = 16.dp),
                            contentDescriptoin = contentDescription1,
                            title = title1
                        )
                    }


                }
            }


//            Box(
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(bottom = 16.dp)
//            ) {
//                floatSOS()
//            }


            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
//                floatBar()
            }


            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                floatNotif()
            }
        }
    }
}

@Composable
fun SOSButton(onCall: (String) -> Unit, onLocationUpdate: (LatLng) -> Unit) {
    val emergencyServices = EmergencyServiceData.getEmergencyServices()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = { /* Placeholder for future functionality */ },
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text("SOS", fontSize = 20.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))
            emergencyServices.forEach { service ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            onCall(service.phoneNumbers.first())
                            onLocationUpdate(service.locations.first().toLatLng()) // Panggil fungsi untuk mengupdate lokasi
                        }
                ) {
                    Image(
                        painter = painterResource(id = service.iconResId),
                        contentDescription = "Icon ${service.name}",
                        modifier = Modifier.size(80.dp)
                    )
                    Text(
                        text = service.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}



