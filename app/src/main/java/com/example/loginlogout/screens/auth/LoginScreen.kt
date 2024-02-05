package com.example.loginlogout.screens.auth

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loginlogout.screens.HomeScreen
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val loginState = remember { mutableStateOf(LoginState.Idle) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Login", style = MaterialTheme.typography.headlineMedium)
        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(), // Hide password characters
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        Button(
            onClick = {
//                loginState.value = loginState.
                viewModel.viewModelScope.launch {

                    val response = viewModel.login(emailState.value, passwordState.value)
                    if (response.isSuccessful){
                        val loginResponse = response.body()
                        val user = loginResponse?.user
                        val token = loginResponse?.token
                        // Now you can use 'user' and 'token' as needed
                        Log.d("User", user.toString())
                        if (token != null) {
                            Log.d("Token", token)
                        }
                    }else{
                        Log.d("login errors", response.errorBody().toString())
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        ) {
            Text(text = "Login")
        }

    }
}

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data object Success : LoginState()
    data class Error(val message: String) : LoginState()
}