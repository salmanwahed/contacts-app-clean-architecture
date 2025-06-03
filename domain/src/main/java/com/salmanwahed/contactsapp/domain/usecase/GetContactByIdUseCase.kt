package com.salmanwahed.contactsapp.domain.usecase

import com.salmanwahed.contactsapp.domain.repository.ContactRepository
import javax.inject.Inject

/**
 * Created by salman on 6/3/25.
 */

class GetContactByIdUseCase @Inject constructor(private val repository: ContactRepository) {
    suspend operator fun invoke(id: Int) = repository.getContactById(id)
}