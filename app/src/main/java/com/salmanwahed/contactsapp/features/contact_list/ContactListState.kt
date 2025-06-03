package com.salmanwahed.contactsapp.features.contact_list

import com.salmanwahed.contactsapp.domain.model.Contact

/**
 * Created by salman on 6/3/25.
 */

data class ContactListState(
    val contacts: List<Contact> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)