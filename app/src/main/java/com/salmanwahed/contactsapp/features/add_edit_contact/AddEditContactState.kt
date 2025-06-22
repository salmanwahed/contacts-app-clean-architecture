package com.salmanwahed.contactsapp.features.add_edit_contact

/**
 * Created by salman on 6/17/25.
 */

data class AddEditContactState(
    val firstName: String = "",
    val lastName: String? = "",
    val phoneNumber: String = "",
    val email: String? = "",
    val id: Int? = null, // Null if adding, non-null if editing
    val pageTitle: String = "Add Contact",
    val isLoading: Boolean = false, // For loading the contact to edit

    // Error Messages
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val phoneNumberError: String? = null,
    val emailError: String? = null
)
