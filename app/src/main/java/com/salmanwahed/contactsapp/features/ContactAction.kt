package com.salmanwahed.contactsapp.features

import com.salmanwahed.contactsapp.domain.model.Contact

/**
 * Created by salman on 6/17/25.
 */

sealed interface ContactAction {
    data object AddContactClicked : ContactAction
    data class EditContactClicked(val contactId: Int) : ContactAction
    data class DeleteContactSwiped(val contact: Contact) : ContactAction
    data object UndoDeleteClicked : ContactAction
    data class FirstNameChanged(val firstName: String) : ContactAction
    data class LastNameChanged(val lastName: String) : ContactAction
    data class PhoneNumberChanged(val phone: String) : ContactAction
    data class EmailChanged(val email: String) : ContactAction
    data object SaveContactClicked : ContactAction
    data object DeleteContactClicked : ContactAction
    data object ConfirmDeleteClicked : ContactAction
}