package com.example.ktorapp.screens.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ktorapp.R
import com.example.ktorapp.data.AppViewModelProvider
import com.example.ktorapp.navigation.routes.MainScreen
import com.example.ktorapp.screens.registrationAlert
import com.example.ktorapp.viewmodel.RegistrationScreenViewModel
import com.example.ktorapp.viewmodel.UserDetails
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: RegistrationScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var message = remember { mutableStateOf( "" ) }
    var isSuccess = remember { mutableStateOf( false ) }
    var passwordShow: Boolean by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val showDialog = remember { mutableStateOf( false ) }
    if (showDialog.value){
        registrationAlert(
            message = message,
            showDialog = showDialog.value,
            isSuccess = isSuccess.value,
            navController = navController,
        ) {
            showDialog.value = false
            if (isSuccess.value) {
                navController.navigate(MainScreen.Splash.name)
            }
        }
    }


    Scaffold (
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .fillMaxWidth()
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.fitness_logo),
                contentDescription = "Fitness Logo",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp)
            )
            Text(
                text = "Register an account",
                fontWeight = FontWeight.Bold,

                style = TextStyle(
                    fontSize = 25.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                "Fill out the following details to create a new a account",
                fontWeight = FontWeight.Medium,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.height(50.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                shape = RoundedCornerShape(10.dp),
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Username", tint = Color.Red)
                },
                placeholder = { Text(text = "Username") },
                label = { Text(text = "Username",color = Color.Black) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp, top = 0.dp, bottom = 0.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                shape = RoundedCornerShape(10.dp),
                placeholder = { Text(text = "Strong Password") },
                label = { Text(text = "Strong Password",color = Color.Black) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp, top = 0.dp, bottom = 0.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black
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
                visualTransformation = if (passwordShow) VisualTransformation.None else PasswordVisualTransformation(),

                )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                shape = RoundedCornerShape(10.dp),
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "First Name", tint = Color.Red)
                },
                singleLine = true,
                placeholder = { Text(text = "First Name") },
                label = { Text(text = "First Name", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp, top = 0.dp, bottom = 0.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                shape = RoundedCornerShape(10.dp),
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Last Name", tint = Color.Red)
                },
                placeholder = { Text(text = "Last Name") },
                label = { Text(text = "Last Name", color = Color.Black) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp, top = 0.dp, bottom = 0.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    // validate here first if input is completed
                    if(username == "" && password == "" && firstName == "" && lastName == ""){
                        message.value = "Please input required fields"
                        showDialog.value = true
                    }else{
                        coroutineScope.launch {
                            // check if user is existing
                            var saveData = 'N'
                            val check = viewModel.selectUser(username)
                            if (check != null) {
                                check.collect {

                                    Timber.i("SAMPLE $it")
                                    if (it == null) {
                                        saveData = 'Y'
                                    }else{
                                        if (it.username.isEmpty()){
                                            saveData = 'Y'
                                        }
                                    }

                                    if (saveData == 'Y') {
                                        message.value = "Successfully registered"
                                        showDialog.value = true
                                        isSuccess.value = true

                                        val userUiState = viewModel.userUiState
                                        userUiState.userDetails =
                                            UserDetails(username, password, firstName, lastName)
                                        viewModel.saveUser()
                                    } else {
                                        message.value = "User already exist"
                                        showDialog.value = true
                                    }
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .absolutePadding(
                        left = 40.dp,
                        right = 40.dp,
                        bottom = 25.dp,
                        top = 25.dp
                    )
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Red),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    "Register",
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(1.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))


            Row(
                modifier = Modifier
                    .padding(11.dp, bottom = 65.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Already a member?",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Box(Modifier.width(7.dp))
                    Text(
                        "Login here",
                        fontSize = 16.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable(
                            onClick = {
                                navController.navigate(MainScreen.Login.name)
                            },
                        )
                    )

                }
            }
        }
    }
}
//fun Register(username: String, password: String, firstName: String, lastName: String) {
//    val user = UserProfile(username, password, firstName, lastName)
//    userList.add(user)
//}





