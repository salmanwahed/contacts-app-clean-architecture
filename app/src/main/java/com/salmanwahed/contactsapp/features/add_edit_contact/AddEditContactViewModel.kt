package com.salmanwahed.contactsapp.features.add_edit_contact

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salmanwahed.contactsapp.domain.exception.InvalidContactException
import com.salmanwahed.contactsapp.domain.model.Contact
import com.salmanwahed.contactsapp.domain.usecase.AddContactUseCase
import com.salmanwahed.contactsapp.domain.usecase.GetContactByIdUseCase
import com.salmanwahed.contactsapp.domain.usecase.UpdateContactUseCase
import com.salmanwahed.contactsapp.features.ContactAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by salman on 6/17/25.
 */

@HiltViewModel
class AddEditContactViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
    private val getContactByIdUseCase: GetContactByIdUseCase,
    savedStateHandle: SavedStateHandle): ViewModel() {

    private val _state = MutableStateFlow(AddEditContactState())
    val state = _state.asStateFlow()
    private val _uiEvent = Channel<AddEditContactUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val id: Int? = savedStateHandle["id"]
        if (id != null && id != -1) {
            _state.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                val contact = getContactByIdUseCase(id)
                if (contact != null) {
                    _state.update {
                        it.copy(
                            firstName = contact.firstName,
                            lastName = contact.lastName,
                            phoneNumber = contact.phoneNumber,
                            email = contact.email,
                            id = contact.id,
                            pageTitle = "Edit Contact",
                            isLoading = false
                        )
                    }
                }

            }
        }
    }

    fun onAction(action: ContactAction) {
        when (action) {
            is ContactAction.FirstNameChanged -> _state.update { it.copy(firstName = action.firstName) }
            is ContactAction.LastNameChanged -> _state.update { it.copy(lastName = action.lastName) }
            is ContactAction.PhoneNumberChanged -> _state.update { it.copy(phoneNumber = action.phone) }
            is ContactAction.EmailChanged -> _state.update { it.copy(email = action.email) }
            ContactAction.SaveContactClicked -> saveContact()
            else -> Unit
        }
    }

    fun saveContact() {
        viewModelScope.launch {
            try {
                val currentState = _state.value
                val contact = Contact(
                    id = currentState.id ?: 0,
                    firstName = currentState.firstName,
                    lastName = currentState.lastName,
                    phoneNumber = currentState.phoneNumber,
                    email = currentState.email?.trim().takeIf { email -> email.isNullOrBlank()}
                )
                if (currentState.id == null) {
                    addContactUseCase(contact)
                } else {
                    updateContactUseCase(contact)
                }
                sendUiEvent(AddEditContactUIEvent.NavigateBack)
                sendUiEvent(AddEditContactUIEvent.ShowSnackbar("Contact saved successfully"))
            } catch (e: InvalidContactException) {
                sendUiEvent(AddEditContactUIEvent.ShowSnackbar(e.message))
            }
        }
    }

    private fun sendUiEvent(uiEvent: AddEditContactUIEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}