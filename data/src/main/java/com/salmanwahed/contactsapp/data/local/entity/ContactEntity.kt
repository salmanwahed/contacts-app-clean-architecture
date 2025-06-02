package com.salmanwahed.contactsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by salman on 6/2/25.
 */


@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String? = null,
    val phoneNumber: String,
    val email: String? = null,
    val imageUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
