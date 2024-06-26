package com.example.ktorapp.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

@Composable
fun Dialogs(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text("OK")
            }
        },
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }

    )
}

@Composable
fun loginAlert(
    showDialog: Boolean,
    onDismiss: () -> Unit
){
    if (showDialog){
        AlertDialog(
            onDismissRequest = { onDismiss },
            confirmButton = {
                TextButton(onClick = onDismiss ) {
                    Text("OK")
                }
            },
            text = {
                Text(text = "Invalid Username and Password!")
            },
            icon = {
                Icon(imageVector = Icons.Filled.Info , contentDescription = "")
            },


        )
    }
}

@Composable
fun registrationAlert(
    message: MutableState<String>,
    showDialog: Boolean,
    isSuccess: Boolean,
    navController: NavController,
    onDismiss: () -> Unit
){
    if (showDialog){
        AlertDialog(
            onDismissRequest = { onDismiss },
            confirmButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text("OK")
                }
            },
            title = {
                Text("")
            },
            text = {
                Text(text = message.value)
            },
            icon = {
                Icon(imageVector = Icons.Filled.Info , contentDescription = "")
            }
        )
    }
}