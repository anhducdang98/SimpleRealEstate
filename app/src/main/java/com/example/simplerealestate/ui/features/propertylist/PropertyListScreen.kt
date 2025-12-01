package com.example.simplerealestate.ui.features.propertylist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.simplerealestate.R

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyListScreen(
    viewModel: PropertyListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.property_list_title)) }
            )
        }
    ) { paddingValues ->
        PropertyListContent(
            uiState = uiState,
            onRetryClick = { viewModel.loadProperties() },
            onLikeClick = { propertyId ->
                viewModel.onEvent(PropertyListEvent.ToggleLike(propertyId))
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun PropertyListContent(
    modifier: Modifier = Modifier,
    uiState: PropertyListUiState,
    onRetryClick: () -> Unit = {},
    onLikeClick: (String) -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (uiState) {
            is PropertyListUiState.Initial -> {
                // Display nothing for initial state
            }

            is PropertyListUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center)
                        .testTag(PropertyListTestTags.LOADING_INDICATOR)
                )
            }

            is PropertyListUiState.Error -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                        .testTag(PropertyListTestTags.ERROR_CONTENT),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.property_list_error_message),
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onRetryClick) {
                        Text(stringResource(R.string.property_list_retry))
                    }
                }
            }

            is PropertyListUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.testTag(PropertyListTestTags.PROPERTY_LIST),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = uiState.properties,
                        key = { it.id }
                    ) { property ->
                        PropertyItem(
                            property = property,
                            onLikeClick = onLikeClick
                        )
                    }
                }
            }
        }
    }
}