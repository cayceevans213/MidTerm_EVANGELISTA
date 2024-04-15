package com.example.ktorapp.screens.ui

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.work.WorkManager
import com.example.ktorapp.data.AppViewModelProvider
import com.example.ktorapp.navigation.routes.MainScreen
import com.example.ktorapp.preferences.PreferencesManager
import com.example.ktorapp.viewmodel.PostViewModel
import com.example.ktorapp.viewmodel.ScreenViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    navController: NavController,
//    drawerState: DrawerState,
    screenViewModel: ScreenViewModel
) {
    val viewModel: PostViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val username = preferencesManager.getData("username", "")
    val coroutineScope = rememberCoroutineScope()

    val application = LocalContext.current.applicationContext as Application
    val workManager = WorkManager.getInstance(application)


    val postsState = viewModel.postsUiState

    Scaffold(
        bottomBar = {
            Button(onClick = {

                screenViewModel.unsetLogin()
                preferencesManager.saveData("login", "")
                preferencesManager.saveData("username", "")
                preferencesManager.saveData("firstName", "")
                preferencesManager.saveData("lastName", "")
                navController.navigate(MainScreen.Splash.name)

            }) {
                Text("SignOut")
            }
        }
    ) {
        Column (modifier = Modifier.padding(it)){
            Button(onClick = {
                coroutineScope.launch {
                    viewModel.getPosts()
                }
            }) {
                Text("Get posts")
            }
            LazyColumn {
                items(postsState.posts) { post ->
                    Card (modifier = Modifier.padding(11.dp)){
                        Text(post.id.toString())
                        Text(post.title)
                        Text(post.body)
                    }
                }
            }
        }
    }


}
