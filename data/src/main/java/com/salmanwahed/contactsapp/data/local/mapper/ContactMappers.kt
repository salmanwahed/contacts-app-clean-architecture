package com.salmanwahed.contactsapp.data.local.mapper

import com.salmanwahed.contactsapp.data.local.entity.ContactEntity
import com.salmanwahed.contactsapp.domain.model.Contact

/**
 * Created by salman on 6/2/25.
 */
 
fun ContactEntity.toDomain(): Contact {
    return Contact(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber,
        email = this.email,
        imageUrl = this.imageUrl
    )
}

fun Contact.toEntity(): ContactEntity {
    return ContactEntity(
        id = this.id ?: -1,
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber,
        email = this.email,
        imageUrl = this.imageUrl
    )
}