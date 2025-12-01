package com.example.simplerealestate.ui.features.propertylist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
@Composable
fun MessageWithAction(
    modifier: Modifier = Modifier,
    message: String,
    actionText: String,
    onActionClick: () -> Unit,
    messageColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = messageColor
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onActionClick) {
            Text(actionText)
        }
    }
}