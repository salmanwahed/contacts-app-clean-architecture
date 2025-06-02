package com.salmanwahed.contactsapp.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.salmanwahed.contactsapp.data.local.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by salman on 6/2/25.
 */

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: ContactEntity): Long

    @Update
    suspend fun updateContact(contact: ContactEntity)

    @Query("SELECT * FROM contacts ORDER BY firstName ASC, lastName ASC")
    fun getAllContacts(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contacts WHERE id = :id")
    suspend fun getContactById(id: Int): ContactEntity?

    @Delete
    suspend fun deleteContact(contact: ContactEntity)

    @Query("DELETE FROM contacts WHERE id = :id")
    suspend fun deleteContactById(id: Int)

    @Query("SELECT COUNT(*) FROM contacts")
    suspend fun getContactCount(): Int

    @Query("SELECT * FROM contacts WHERE firstName LIKE '%' || :query || '%' OR lastName LIKE '%' || :query || '%' ORDER BY firstName ASC, lastName ASC")
    fun searchContacts(query: String): Flow<List<ContactEntity>>
}