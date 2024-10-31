package com.mawar.bsecure.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mawar.bsecure.model.sos.Sos
import com.mawar.bsecure.ui.viewModel.sos.SosViewModel

@Composable
fun SosScreen(viewModel: SosViewModel) {
    val sosList by viewModel.sosList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        sosList.forEach { sos ->
            Text(text = sos.nama_instansi, modifier = Modifier.padding(8.dp))
        }

        Button(onClick = {
            val newSos = Sos(
//                id = "sample_id",
                nama_instansi = "New Instansi",
                jenis = "Type",
                negara = "Country",
                provinsi = "Province",
                kota = "City",
                kecamatan = "District",
                kelurahan = "Village",
                no_telp = "1234567890"
            )
            viewModel.addSos(newSos)
        }) {
            Text("Add Sample Sos")
        }

    }
}
