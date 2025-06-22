package com.salmanwahed.contactsapp.features.add_edit_contact

/**
 * Created by salman on 6/22/25.
 */

sealed interface AddEditContactAction {
  data class FirstNameChanged(val firstName: String) : AddEditContactAction
  data class LastNameChanged(val lastName: String) : AddEditContactAction
  data class PhoneNumberChanged(val phone: String) : AddEditContactAction
  data class EmailChanged(val email: String) : AddEditContactAction
  data object SaveContactClicked : AddEditContactAction
  data class FocusChanged(val fieldName: String, val isFocused: Boolean) : AddEditContactAction
}