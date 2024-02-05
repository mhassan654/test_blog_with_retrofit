package com.example.loginlogout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginlogout.screens.HomeScreen
import com.example.loginlogout.screens.auth.LoginScreen
import com.example.loginlogout.screens.auth.LoginViewModel
import com.example.loginlogout.ui.theme.LoginLogoutTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginLogoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoginLogoutTheme {
        Greeting("Android")
    }
}

object NavGraph {
    const val root = "root"
    const val login = "login"
    const val home = "home"
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavGraph.login) {
//        composable(NavGraph.login) {
//            LoginScreen(viewModel =
//            })
//        }
        composable(NavGraph.home) {
            HomeScreen(AuthService.currentUser ?: return)
        }
    }
}
