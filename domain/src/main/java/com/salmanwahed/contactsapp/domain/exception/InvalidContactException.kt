package com.salmanwahed.contactsapp.domain.exception

/**
 * Created by salman on 6/17/25.
 */

open class InvalidContactException(override val message: String) : Exception(message)


class InvalidFirstNameException(message: String = "First name cannot be empty.") : InvalidContactException(message)
class InvalidPhoneNumberException(message: String = "Phone number cannot be empty.") : InvalidContactException(message)
class InvalidEmailException(message: String = "Please enter a valid email address.") : InvalidContactException(message)