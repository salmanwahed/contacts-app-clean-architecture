package com.salmanwahed.contactsapp.features.add_edit_contact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by salman on 6/17/25.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditContactScreen(
    navController: NavController,
    viewModel: AddEditContactViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is AddEditContactUIEvent.NavigateBack -> {
                   navController.popBackStack()
                }
                is AddEditContactUIEvent.SaveContactClicked -> {
                    viewModel.saveContact()
                }
                is AddEditContactUIEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = "Contact saved!")
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(state.pageTitle) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onAction(AddEditContactAction.SaveContactClicked) }) {
                Icon(Icons.Default.Done, contentDescription = "Save Contact")
            }
        }
    ) {
        innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                AddEditContactForm(state = state, viewModel=viewModel)
            }
        }

    }

}

@Composable
fun AddEditContactForm(state: AddEditContactState, viewModel: AddEditContactViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = state.firstName,
            onValueChange = { viewModel.onAction(AddEditContactAction.FirstNameChanged(it)) },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            singleLine = true
        )
        OutlinedTextField(
            value = state.lastName?: "",
            onValueChange = { viewModel.onAction(AddEditContactAction.LastNameChanged(it)) },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            singleLine = true
        )
        OutlinedTextField(
            value = state.phoneNumber,
            onValueChange = { viewModel.onAction(AddEditContactAction.PhoneNumberChanged(it)) },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true
        )
        OutlinedTextField(
            value = state.email?: "",
            onValueChange = { viewModel.onAction(AddEditContactAction.EmailChanged(it)) },
            label = { Text("Email (Optional)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

    }
}
