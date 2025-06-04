package com.salmanwahed.contactsapp.features.add_edit_contact

import com.salmanwahed.contactsapp.domain.model.Contact

/**
 * Created by salman on 6/4/25.
 */

sealed interface AddEditContactUIEvents {
  data class SaveContactClicked(val contact: Contact): AddEditContactUIEvents
}