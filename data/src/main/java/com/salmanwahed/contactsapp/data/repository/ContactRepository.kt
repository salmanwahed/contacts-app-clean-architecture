package com.salmanwahed.contactsapp.data.repository

import com.salmanwahed.contactsapp.domain.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * Created by salman on 6/2/25.
 */

interface ContactRepository {
  fun getAllContacts(): Flow<List<Contact>>
  suspend fun getContactById(id: Int): Contact?
  suspend fun insertContact(contact: Contact): Long
  suspend fun deleteContact(contact: Contact)
  suspend fun updateContact(contact: Contact)
  fun searchContacts(query: String): Flow<List<Contact>>
  suspend fun getContactCount(): Int
  suspend fun deleteContactById(id: Int)
}