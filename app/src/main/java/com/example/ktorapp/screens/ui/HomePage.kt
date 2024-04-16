package com.example.ktorapp.screens.ui

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomAppBarDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRowDefaults.contentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.work.WorkManager
import coil.annotation.ExperimentalCoilApi
import com.example.ktorapp.data.AppViewModelProvider
import com.example.ktorapp.navigation.routes.MainScreen
import com.example.ktorapp.preferences.PreferencesManager
import com.example.ktorapp.viewmodel.PhotoViewModel
import com.example.ktorapp.viewmodel.PostViewModel
import com.example.ktorapp.viewmodel.ScreenViewModel
import kotlinx.coroutines.launch
import coil.compose.rememberImagePainter

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun HomePage(
    navController: NavController,
//    drawerState: DrawerState,
    screenViewModel: ScreenViewModel
) {
    val viewModel: PostViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val photoViewModel: PhotoViewModel = viewModel(factory = AppViewModelProvider.Factory)


    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val username = preferencesManager.getData("username", "")
    val coroutineScope = rememberCoroutineScope()

    val application = LocalContext.current.applicationContext as Application
    val workManager = WorkManager.getInstance(application)


    val postsState = viewModel.postsUiState

    val photoState = photoViewModel.photoUiState

    var showPostData by remember { mutableStateOf(false) }
    var showPhotoData by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Button(
                        onClick = {
                            navController.navigate(MainScreen.Profile.name)
                        }
                    ) {
                        Text("Profile")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Gray),
                actions = {

                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Gray,
            ) {

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

        }
    ) {
        Column(modifier = Modifier.background(Color.White).fillMaxSize().padding(it)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.getPosts()
                            showPostData = true
                            showPhotoData = false
                        }
                    },

                ) {
                    Text("Get posts")
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            photoViewModel.getPhoto()
                            showPhotoData = true
                            showPostData = false
                        }
                    },

                ) {
                    Text("Get Photo")
                }
            }

            if (showPostData) {
                LazyColumn {
                    items(postsState.posts.take(5)) { post ->
                        Card(modifier = Modifier.padding(11.dp)) {
                            Text(post.id.toString())
                            Text(post.title)
                            Text(post.body)
                        }
                    }
                }
            }

            if (showPhotoData) {
                LazyColumn {
                    items(photoState.photo.take(5)) { photo ->
                        Card(modifier = Modifier.padding(11.dp)) {
                            Text(photo.id.toString())
                            Text(photo.title)
                            Image(
                                painter = rememberImagePainter(photo.url),
                                contentDescription = "Photo",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }


}
