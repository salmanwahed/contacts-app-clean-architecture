package com.salmanwahed.contactsapp.domain.usecase

import com.salmanwahed.contactsapp.domain.model.Contact
import com.salmanwahed.contactsapp.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by salman on 6/3/25.
 */

class GetContactsUseCase @Inject constructor(private val repository: ContactRepository) {
    operator fun invoke(): Flow<List<Contact>> {
        return repository.getAllContacts()
    }
}