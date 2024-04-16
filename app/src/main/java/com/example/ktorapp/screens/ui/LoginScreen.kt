package com.example.ktorapp.screens.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ktorapp.R
import com.example.ktorapp.data.AppViewModelProvider
import com.example.ktorapp.model.UserProfile
import com.example.ktorapp.navigation.routes.MainScreen
import com.example.ktorapp.preferences.PreferencesManager
import com.example.ktorapp.screens.loginAlert
import com.example.ktorapp.viewmodel.LoginScreenViewModel
import com.example.ktorapp.viewmodel.ScreenViewModel
import com.example.ktorapp.viewmodel.UserDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    screenViewModel: ScreenViewModel,
    viewModel: LoginScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isChecked = remember { mutableStateOf(false) }
    var passwordShow: Boolean by remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    if (openDialog.value){
        loginAlert(openDialog.value) { openDialog.value = false }
    }
    val coroutineScope = rememberCoroutineScope()
    var isTextFieldFocused by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    val scrollState = rememberScrollState()


    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(295.dp).verticalScroll(scrollState),
                containerColor = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 25.dp, end = 25.dp, top = 0.dp, bottom = 0.dp)
                        .fillMaxSize()
                        .fillMaxWidth()
                        .background(Color.White),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val loginState = viewModel.userUiState
                                loginState.userDetails = UserDetails(email, password)
                                val flow : Flow<UserProfile?>? = viewModel.selectUser()

                                if (flow != null) {
                                    flow.collect {

                                        if (it != null) {
                                            if (it.username.isEmpty()){
                                                openDialog.value = true
                                            }else {
                                                screenViewModel.setLogin()

                                                preferencesManager.saveData("login", "true")
                                                preferencesManager.saveData("username", it.username)
                                                preferencesManager.saveData(
                                                    "firstName",
                                                    it.firstName
                                                )
                                                preferencesManager.saveData("lastName", it.lastName)

                                                navController.navigate(MainScreen.Splash.name)
                                            }
                                        }else{
                                            openDialog.value = true
                                        }
                                    }
                                }else{
                                    // no record found
                                    openDialog.value = true
                                }
                            }
                        },
                        modifier = Modifier
                            .absolutePadding(
                                left = 5.dp,
                                right = 5.dp,

                            )
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color.Red),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(
                            text = "Sign In",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Don't have account? ",
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = Color.Black,
                        )
                        TextButton(
                            onClick = { navController.navigate(MainScreen.RegistrationScreen.name) })
                        {
                            Text(
                                text = "Sign Up",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 17.sp,
                                color = Color.Red,
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .fillMaxWidth()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.fitness_logo),
                contentDescription = "Fitness Logo",
                modifier = Modifier.size(250.dp)
            )
            Text(
                text = "Welcome back!",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.Black
            )
            Text(
                text = "Sign in to access your account",
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(50.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp),
                value = email,
                shape = RoundedCornerShape(10.dp),
                onValueChange = { email = it },
                label = { Text(text = "Username", color = Color.Black) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black
                ),
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email" , tint = Color.Red)
                },
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp),
                value = password,
                shape = RoundedCornerShape(10.dp),
                onValueChange = { password = it },
                label = { Text(text = "Password", color = Color.Black) },

                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,

                    unfocusedBorderColor = Color.Gray, // Change to desired color when not focused


                ),
                trailingIcon = {
                    val image = if (passwordShow)
                        Icons.Filled.Visibility
                    else
                        Icons.Filled.VisibilityOff

                    val description = if (passwordShow) "Hide Password" else "Show Password"

                    IconButton(onClick = {
                        passwordShow = !passwordShow
                    }) {
                        Icon(imageVector = image, contentDescription =  description, tint = Color.Red)
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordShow) VisualTransformation.None else PasswordVisualTransformation(),

            )

        }
    }
}