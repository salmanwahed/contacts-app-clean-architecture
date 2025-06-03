package com.salmanwahed.contactsapp.features.contact_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.salmanwahed.contactsapp.domain.model.Contact

/**
 * Created by salman on 6/3/25.
 */

@Composable
fun ContactListScreen(viewModel: ContactListViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text(
                text = state.error ?: "An unknown error occurred",
                color = MaterialTheme.colorScheme.error
            )
        } else {
            ContactLazyList(contacts = state.contacts)
        }
    }
}

@Composable
fun ContactLazyList(
    contacts: List<Contact>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(items = contacts, key = { contact -> contact.id }){ contact ->
            ContactLazyListItem(contact = contact)
        }
    }
}

@Composable
fun ContactLazyListItem(
    contact: Contact,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "${contact.firstName} ${contact.lastName}",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = contact.phoneNumber,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}