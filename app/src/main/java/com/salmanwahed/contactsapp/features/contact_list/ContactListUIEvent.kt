package com.salmanwahed.contactsapp.features.contact_list

/**
 * Created by salman on 6/4/25.
 */

sealed interface ContactListUIEvent {
  data class AddContactClicked(val route: String) : ContactListUIEvent
  data class ShowSnackbar(val message: String, val action: String? = null): ContactListUIEvent
  data object NavigateBack : ContactListUIEvent
}