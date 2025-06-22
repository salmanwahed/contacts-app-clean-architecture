package com.salmanwahed.contactsapp.features.contact_list

import com.salmanwahed.contactsapp.domain.model.Contact
import com.salmanwahed.contactsapp.features.add_edit_contact.AddEditContactAction

/**
 * Created by salman on 6/22/25.
 */

sealed interface ContactListAction {
  data class AddEditContactClicked(val contactId: Int? = null) : ContactListAction
  data class DeleteContactSwiped(val contact: Contact) : ContactListAction
  data object UndoDeleteClicked : ContactListAction
}