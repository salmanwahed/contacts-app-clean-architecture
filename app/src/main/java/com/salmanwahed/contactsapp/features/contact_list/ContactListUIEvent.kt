package com.salmanwahed.contactsapp.features.contact_list

/**
 * Created by salman on 6/4/25.
 */

sealed interface ContactListUIEvent {
  data object AddContactClicked: ContactListUIEvent
}