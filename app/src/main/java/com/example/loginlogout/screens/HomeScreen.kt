package com.example.loginlogout.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.loginlogout.dataModel.User
import com.example.loginlogout.di.AuthService

@Composable
fun HomeScreen(user: User) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Welcome, ${user.name}!", style = MaterialTheme.typography.headlineMedium)
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        ) {
            Text(text = "Logout")
        }
    }
}
