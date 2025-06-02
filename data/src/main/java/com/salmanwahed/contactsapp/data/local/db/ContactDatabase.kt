package com.salmanwahed.contactsapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.salmanwahed.contactsapp.data.local.entity.ContactEntity

/**
 * Created by salman on 6/2/25.
 */

@Database(
    entities = [ContactEntity::class],
    version = 1,
    exportSchema = true
)
abstract class ContactDatabase: RoomDatabase() {
  abstract val contactDao: ContactDao
}