package com.salmanwahed.contactsapp.features.add_edit_contact

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salmanwahed.contactsapp.domain.exception.InvalidContactException
import com.salmanwahed.contactsapp.domain.exception.InvalidEmailException
import com.salmanwahed.contactsapp.domain.exception.InvalidPhoneNumberException
import com.salmanwahed.contactsapp.domain.model.Contact
import com.salmanwahed.contactsapp.domain.usecase.AddContactUseCase
import com.salmanwahed.contactsapp.domain.usecase.GetContactByIdUseCase
import com.salmanwahed.contactsapp.domain.usecase.UpdateContactUseCase
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
    private val getContactByIdUseCase: GetContactByIdUseCase
    ): ViewModel() {

    private val _state = MutableStateFlow(AddEditContactState())
    val state = _state.asStateFlow()
    private val _uiEvent = Channel<AddEditContactUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun initWithId(id: Int?) {
        if (id != null) {
            _state.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                val contact = getContactByIdUseCase(id)
                if (contact != null) {
                    _state.update {
                        it.copy(
                            id = contact.id,
                            firstName = contact.firstName,
                            lastName = contact.lastName,
                            phoneNumber = contact.phoneNumber,
                            email = contact.email,
                            pageTitle = "Edit Contact",
                            isLoading = false
                        )
                    }
                }

            }
        }
    }

    fun onAction(action: AddEditContactAction) {
        when (action) {
            is AddEditContactAction.FirstNameChanged -> _state.update { it.copy(firstName = action.firstName) }
            is AddEditContactAction.LastNameChanged -> _state.update { it.copy(lastName = action.lastName) }
            is AddEditContactAction.PhoneNumberChanged -> _state.update { it.copy(phoneNumber = action.phone) }
            is AddEditContactAction.EmailChanged -> _state.update { it.copy(email = action.email) }
            is AddEditContactAction.DismissisSuccessDialog -> _state.update { it.copy(isSuccessDialogVisible = false) }
            is AddEditContactAction.ConfirmSuccessDialog -> {
                _state.update { it.copy(isSuccessDialogVisible = false) }
                sendUiEvent(AddEditContactUIEvent.NavigateBack)
            }
            is AddEditContactAction.FocusChanged -> {
                if (action.isFocused) {
                    when(action.fieldName) {
                        "firstName" -> _state.update { it.copy(firstNameError = null) }
                        "phoneNumber" -> _state.update { it.copy(phoneNumberError = null) }
                        "email" -> _state.update { it.copy(emailError = null) }
                    }
                }
            }
            is AddEditContactAction.SaveContactClicked -> saveContact()
            else -> Unit
        }
    }

    fun saveContact() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val currentState = _state.value
                val contact = Contact(
                    firstName = currentState.firstName,
                    lastName = currentState.lastName,
                    phoneNumber = currentState.phoneNumber,
                    email = currentState.email
                )
                if (currentState.id == null) {
                    addContactUseCase(contact)
                } else {
                    Log.i("Contact -> Update", contact.toString())
                    contact.id = currentState.id
                    updateContactUseCase(contact)
                }
                _state.update { it.copy(isSuccessDialogVisible = true, isLoading = false) }
            } catch (e: InvalidContactException) {
                Log.i(TAG, e.message)
                _state.update { it.copy(firstNameError = e.message, isLoading = false) }
            } catch (e: InvalidPhoneNumberException) {
                Log.i(TAG, e.message)
                _state.update { it.copy(phoneNumberError = e.message, isLoading = false) }
            } catch (e: InvalidEmailException) {
                Log.i(TAG, e.message)
                _state.update { it.copy(emailError = e.message, isLoading = false) }
            }

        }
    }

    private fun sendUiEvent(uiEvent: AddEditContactUIEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    companion object {
        const val TAG = "ContactApp"
    }
}