package com.salmanwahed.contactsapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salmanwahed.contactsapp.features.add_edit_contact.AddEditContactScreen
import com.salmanwahed.contactsapp.features.contact_list.ContactListScreen

/**
 * Created by salman on 6/22/25.
 */

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.ContactList.route) {
        composable(route = Screen.ContactList.route) {
            ContactListScreen(navController=navController)
        }
        composable(route = Screen.AddEditContact.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            AddEditContactScreen(navController=navController, id=id)
        }
    }
}