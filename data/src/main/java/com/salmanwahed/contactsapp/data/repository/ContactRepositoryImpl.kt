package com.salmanwahed.contactsapp.data.repository

import com.salmanwahed.contactsapp.data.local.db.ContactDao
import com.salmanwahed.contactsapp.data.local.mapper.toDomain
import com.salmanwahed.contactsapp.data.local.mapper.toEntity
import com.salmanwahed.contactsapp.domain.model.Contact
import com.salmanwahed.contactsapp.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by salman on 6/2/25.
 */

class ContactRepositoryImpl @Inject constructor(private val contactDao: ContactDao):
    ContactRepository {
    override fun getAllContacts(): Flow<List<Contact>> {
        return contactDao.getAllContacts().map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getContactById(id: Int): Contact? {
        return contactDao.getContactById(id)?.toDomain()
    }

    override suspend fun insertContact(contact: Contact): Long {
        return contactDao.insertContact(contact.toEntity())
    }

    override suspend fun deleteContact(contact: Contact) {
        return contactDao.deleteContact(contact.toEntity())
    }

    override suspend fun updateContact(contact: Contact) {
        return contactDao.updateContact(contact.toEntity())
    }

    override fun searchContacts(query: String): Flow<List<Contact>> {
        return contactDao.searchContacts(query).map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getContactCount(): Int {
        return contactDao.getContactCount()
    }

    override suspend fun deleteContactById(id: Int) {
        return contactDao.deleteContactById(id)
    }
}