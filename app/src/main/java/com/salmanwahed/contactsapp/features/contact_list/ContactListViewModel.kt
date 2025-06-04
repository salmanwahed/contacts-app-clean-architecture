package com.salmanwahed.contactsapp.features.contact_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salmanwahed.contactsapp.domain.usecase.GetContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Created by salman on 6/3/25.
 */

@HiltViewModel
class ContactListViewModel @Inject constructor(private val getContactsUseCase: GetContactsUseCase): ViewModel() {
    private val _state = MutableStateFlow(ContactListState())
    val state: StateFlow<ContactListState> = _state.asStateFlow()

    init {
        loadContacts()
    }

    private fun onUIEvent(uiEvent: ContactListUIEvents) {
        when(uiEvent) {
            is ContactListUIEvents.AddContactClicked -> {

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
}