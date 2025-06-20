package com.salmanwahed.contactsapp.features.contact_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salmanwahed.contactsapp.domain.usecase.GetContactsUseCase
import com.salmanwahed.contactsapp.features.ContactAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by salman on 6/3/25.
 */

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ContactListState())
    val state: StateFlow<ContactListState> = _state.asStateFlow()

    private val _uiEvent = Channel<ContactListUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadContacts()
    }

    fun onAction(action: ContactAction) {
        when(action) {
            is ContactAction.AddContactClicked -> {
                sendUiEvent(ContactListUIEvent.AddContactClicked)
            }
            else -> {
                Log.d("ContactListViewModel", "Unknown action: $action")
            }
        }
    }

    private fun loadContacts() {
        getContactsUseCase()
            .onStart {
                _state.update { it.copy(isLoading = true, error = null) }
            }
            .onEach { contacts ->
                _state.update {
                    it.copy(isLoading = false, contacts = contacts)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun sendUiEvent(event: ContactListUIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}