package com.mawar.bsecure.ui.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mawar.bsecure.ui.view.Beranda.TopBars
import com.mawar.bsecure.ui.view.Beranda.floatBar
import com.mawar.bsecure.ui.view.Beranda.floatNotif
import com.mawar.bsecure.ui.viewModel.location.LocationViewModel
import kotlinx.coroutines.launch
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.DrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.DrawerValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.mawar.bsecure.ui.viewModel.fakeCall.TimerViewModel


@Composable
fun MainScreen(viewModel: LocationViewModel = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState(drawerState = rememberDrawerState(initialValue = DrawerValue.Closed))
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = currentBackStackEntry?.destination?.route



    // Gunakan Scaffold untuk menampung konten utama
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            if(currentRoute != null && currentRoute != "incoming_call/{contactName}"){
                CustomBottomNavigation(navController)
            } },
        topBar = {
            if(currentRoute != null && currentRoute != "incoming_call/{contactName}"){
                TopBars()
            } },
        drawerContent = {
            NavigationDrawer(
                onSettingClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                    navController.navigate("setting_screen")
                },
                onLogOutClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                    // Tambahkan logika logout di sini
                }
            )
        }
    ){ innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize() // Pastikan mengisi ukuran penuh
            ) {
                NavHostContainer(navController = navController, modifier = Modifier.fillMaxSize())

                // Menambahkan tombol untuk membuka drawer
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart) // Pastikan posisi tombol benar
                ) {
                    if(currentRoute != null && currentRoute != "incoming_call/{contactName}"){
                    floatBar(onMenuClick = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open() // Open drawer using scaffoldState
                        }
                    })
                }
                }



                // Menambahkan tombol notifikasi
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd) // Pastikan posisi tombol benar
                ) {if(currentRoute != null && currentRoute != "incoming_call/{contactName}"){
                    floatNotif()
                }

                }
            }
        }
    }





@Composable
fun CustomBottomNavigation(navController: NavHostController) {
    val items = listOf(
        Screen.Community,
        Screen.FakeCall,
        Screen.Location,
        Screen.Profile
    )

    BottomAppBar  (modifier = Modifier.background(color = Color(0xFF9A9AB2)),
        contentColor = Color(0xFFF6F6F6),
        contentPadding = PaddingValues(horizontal = 0.dp),
        containerColor = Color(0xFF7346A5)
    ) {
        items.forEachIndexed { index, screen ->
            if (index ==9) {
                Spacer(Modifier.weight(1f, fill = true))
            } else {
                BottomNavigationItem(
                    icon = { Icon(screen.icon, contentDescription = screen.title) },
                    label = { Text(screen.title) },
                    selected = false,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun NavHostContainer(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = Screen.Community.route, modifier = modifier) {
        composable(Screen.Community.route) { CommunityScreen() }
        composable(Screen.FakeCall.route) { backStackEntry ->
            val timerViewModel: TimerViewModel = viewModel()
            FakeCallScreen(
                navController = navController,
                timerViewModel = timerViewModel
            ) }
        composable(
            route = "incoming_call/{contactName}",
            arguments = listOf(navArgument("contactName") { defaultValue = "Unknown" })
        ) { backStackEntry ->
            val contactName = backStackEntry.arguments?.getString("contactName") ?: "Unknown"
            IncomingCallScreen(contactName = contactName)
        }
        composable(Screen.Location.route) { LocationScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
    }
}





