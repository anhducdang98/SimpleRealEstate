package com.example.simplerealestate.ui.features.propertylist.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.simplerealestate.R
import com.example.simplerealestate.ui.features.propertylist.PropertyListTestTags
import com.example.simplerealestate.ui.features.propertylist.components.MessageWithAction
import com.example.simplerealestate.ui.features.propertylist.components.PropertyItem
import com.example.simplerealestate.ui.features.propertylist.viewmodel.PropertyListEvent
import com.example.simplerealestate.ui.features.propertylist.viewmodel.PropertyListUiState
import com.example.simplerealestate.ui.features.propertylist.viewmodel.PropertyListViewModel

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

    val onRetryClick = remember<() -> Unit> { { viewModel.loadProperties() } }
    val onLikeClick = remember<(String) -> Unit> { { propertyId ->
        viewModel.onEvent(PropertyListEvent.ToggleLike(propertyId))
    } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.property_list_title), style = MaterialTheme.typography.headlineSmall) },
            )
        }
    ) { paddingValues ->
        PropertyListContent(
            uiState = uiState,
            onRetryClick = onRetryClick,
            onLikeClick = onLikeClick,
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
                MessageWithAction(
                    message = stringResource(R.string.property_list_error_message),
                    actionText = stringResource(R.string.property_list_retry),
                    onActionClick = onRetryClick,
                    messageColor = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag(PropertyListTestTags.ERROR_CONTENT)
                )
            }

            is PropertyListUiState.Success -> {
                if (uiState.properties.isEmpty()) {
                    MessageWithAction(
                        message = stringResource(R.string.property_list_empty_message),
                        actionText = stringResource(R.string.property_list_reload),
                        onActionClick = onRetryClick,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag(PropertyListTestTags.EMPTY_CONTENT)
                    )
                } else {
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
}