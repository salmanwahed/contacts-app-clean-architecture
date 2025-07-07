package com.salmanwahed.contactsapp.core.navigation

/**
 * Created by salman on 6/4/25.
 */

sealed class Screen(val route: String) {
  data object ContactList : Screen("contact_list_screen")

  data object AddEditContact : Screen("add_edit_contact_screen?id={id}") {
    const val ROUTE_PREFIX = "add_edit_contact_screen"
    fun createRoute(id: String?): String {
      return if (id != null) {
        "$ROUTE_PREFIX?id=$id"
      } else {
        ROUTE_PREFIX
      }
    }
  }
}