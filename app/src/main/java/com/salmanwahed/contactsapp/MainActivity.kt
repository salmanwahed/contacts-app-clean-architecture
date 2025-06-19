package com.salmanwahed.contactsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salmanwahed.contactsapp.core.navigation.Screen
import com.salmanwahed.contactsapp.features.add_edit_contact.AddEditContactScreen
import com.salmanwahed.contactsapp.features.contact_list.ContactListScreen
import com.salmanwahed.contactsapp.ui.theme.ContactsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactsAppTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.ContactList.route) {
        composable(Screen.ContactList.route) {
            ContactListScreen(navController=navController)
        }
        composable(Screen.AddEditContact.route,
            arguments = listOf(
                navArgument("id") {
                type = NavType.IntType
                defaultValue = -1
                }
            )
        ) {
            AddEditContactScreen(navController=navController)
        }
    }
}
