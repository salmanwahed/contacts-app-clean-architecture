package com.salmanwahed.contactsapp.domain.model

/**
 * Created by salman on 6/2/25.
 */

data class Contact(
  var id: Int? = null,
  val firstName: String,
  val lastName: String? = null,
  val phoneNumber: String,
  val email: String? = null,
  val imageUrl: String? = null
)