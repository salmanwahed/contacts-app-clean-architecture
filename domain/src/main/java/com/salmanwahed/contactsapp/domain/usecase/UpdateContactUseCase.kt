package com.salmanwahed.contactsapp.domain.usecase

import com.salmanwahed.contactsapp.domain.exception.InvalidContactException
import com.salmanwahed.contactsapp.domain.exception.InvalidEmailException
import com.salmanwahed.contactsapp.domain.exception.InvalidFirstNameException
import com.salmanwahed.contactsapp.domain.exception.InvalidPhoneNumberException
import com.salmanwahed.contactsapp.domain.model.Contact
import com.salmanwahed.contactsapp.domain.repository.ContactRepository
import javax.inject.Inject

/**
 * Created by salman on 6/17/25.
 */

class UpdateContactUseCase @Inject constructor(private val repository: ContactRepository) {
  suspend operator fun invoke(contact: Contact) {
    if (contact.firstName.isNullOrBlank()){
      throw InvalidFirstNameException()
    }
    if (contact.phoneNumber.isNullOrBlank()){
      throw InvalidPhoneNumberException()
    }
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    if (contact.email?.isNotBlank() == true && !contact.email.matches(emailRegex)) {
      throw InvalidEmailException()
    }
    return repository.updateContact(contact)

  }
}