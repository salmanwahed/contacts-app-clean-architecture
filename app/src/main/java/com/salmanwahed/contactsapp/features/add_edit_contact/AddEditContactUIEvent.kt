package com.salmanwahed.contactsapp.features.add_edit_contact

import android.os.Message
import com.salmanwahed.contactsapp.domain.model.Contact

/**
 * Created by salman on 6/4/25.
 */

sealed interface AddEditContactUIEvent {
  data class SaveContactClicked(val contact: Contact): AddEditContactUIEvent
  data object NavigateBack: AddEditContactUIEvent
  data class ShowSnackbar(val message: String): AddEditContactUIEvent
}