package com.example.simplerealestate.ui.features.propertylist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.example.simplerealestate.R
import com.example.simplerealestate.domain.model.Property

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
@Composable
fun PropertyItem(
    modifier: Modifier = Modifier,
    property: Property,
    onLikeClick: (String) -> Unit = {},
) {
    val listing = property.listing
    val imageUrl = listing.localization.de?.bannerImage
    val title = listing.localization.de?.text?.title.orEmpty()
    val formattedPrice = listing.prices.formattedPrice
    val formattedAddress = listing.address.formattedAddress

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            ) {
                SubcomposeAsyncImage(
                    model = imageUrl,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize(),
                    loading = { ImagePlaceholder() },
                    error = { ImagePlaceholder() }
                )

                PriceTag(
                    price = formattedPrice,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                )

                LikeButton(
                    isLiked = property.isLiked,
                    onClick = { onLikeClick(property.id) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                )
            }
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Property name
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Property address
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        modifier = Modifier.weight(1f),
                        text = formattedAddress,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun PriceTag(
    modifier: Modifier = Modifier,
    price: String,
) {
    Text(
        text = price,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

@Composable
private fun LikeButton(
    modifier: Modifier = Modifier,
    isLiked: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = stringResource(
                if (isLiked) R.string.content_description_remove_from_liked
                else R.string.content_description_add_to_liked
            ),
            tint = if (isLiked) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun ImagePlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(48.dp)
        )
    }
}