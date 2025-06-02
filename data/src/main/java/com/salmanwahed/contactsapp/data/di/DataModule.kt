package com.salmanwahed.contactsapp.data.di

import android.content.Context
import androidx.room.Room
import com.salmanwahed.contactsapp.data.local.db.ContactDao
import com.salmanwahed.contactsapp.data.local.db.ContactDatabase
import com.salmanwahed.contactsapp.data.repository.ContactRepositoryImpl
import com.salmanwahed.contactsapp.domain.repository.ContactRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by salman on 6/2/25.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

  @Binds
  @Singleton
  abstract fun bindContactRepository(contactRepositoryImpl: ContactRepositoryImpl): ContactRepository

  companion object {
    @Provides
    @Singleton
    fun provideContactDatabase(@ApplicationContext context: Context): ContactDatabase {
      return Room.databaseBuilder(
        context,
        ContactDatabase::class.java,
        "contacts.db"
      ).build()
    }

    @Provides
    @Singleton
    fun provideContactDao(contactDatabase: ContactDatabase): ContactDao {
      return contactDatabase.contactDao
    }
  }

}