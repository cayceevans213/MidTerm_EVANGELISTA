package com.example.ktorapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ktorapp.navigation.routes.MainScreen
import com.example.ktorapp.preferences.PreferencesManager
import com.example.ktorapp.screens.ui.HomePage
import com.example.ktorapp.screens.ui.LoginScreen
import com.example.ktorapp.screens.ui.RegistrationScreen
import com.example.ktorapp.screens.ui.SplashScreen
import com.example.ktorapp.viewmodel.ScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchdulixApp (
    screenViewModel: ScreenViewModel
){
    val navController: NavHostController = rememberNavController()

    Scaffold {
        NavHost(
            navController = navController,
            startDestination = MainScreen.Splash.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = MainScreen.Splash.name) {
                SplashScreen( navController, screenViewModel )
            }
            composable(route = MainScreen.CheckLogin.name) {
                CheckLogin( screenViewModel )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckLogin( screenViewModel: ScreenViewModel ){
    val navController: NavHostController = rememberNavController()

    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val data = preferencesManager.getData("login", "")

    if (data == ""){
        MainLogin(navController, screenViewModel)
    }else{
        MainHomeScreen(navController, screenViewModel)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLogin(
    navController: NavHostController,
    screenViewModel: ScreenViewModel
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
        }
        NavHost(
            navController = navController,
            startDestination = MainScreen.Login.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = MainScreen.Login.name) {
                LoginScreen( navController, screenViewModel )
            }
            composable(route = MainScreen.RegistrationScreen.name) {
                RegistrationScreen( navController )
            }
            composable(route = MainScreen.HomePage.name) {
                HomePage( navController, screenViewModel )
            }
            composable(route = MainScreen.CheckLogin.name) {
                CheckLogin( screenViewModel )
            }
            composable(route = MainScreen.Splash.name) {
                SplashScreen( navController, screenViewModel )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHomeScreen(
    navController: NavHostController,
    screenViewModel: ScreenViewModel
){


    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    Scaffold(
        bottomBar = {

        }
    ) {
        Column(modifier = Modifier.padding(it)) {
        }
        NavHost(
            navController = navController,
            startDestination = MainScreen.HomePage.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = MainScreen.Login.name) {
                LoginScreen(navController, screenViewModel)
            }
            composable(route = MainScreen.RegistrationScreen.name) {
                RegistrationScreen(navController)
            }
            composable(route = MainScreen.HomePage.name) {
                HomePage(navController, screenViewModel)
            }
            composable(route = MainScreen.CheckLogin.name) {
                CheckLogin(screenViewModel)
            }
            composable(route = MainScreen.Splash.name) {
                SplashScreen(navController, screenViewModel)
            }

        }
    }
}
