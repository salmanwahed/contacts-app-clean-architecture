package com.salmanwahed.contactsapp.features.contact_list

import com.salmanwahed.contactsapp.domain.model.Contact

/**
 * Created by salman on 6/22/25.
 */

sealed interface ContactListAction {
  data class AddEditContactClicked(val id: String?) : ContactListAction
  data class DeleteContactSwiped(val contact: Contact) : ContactListAction
}