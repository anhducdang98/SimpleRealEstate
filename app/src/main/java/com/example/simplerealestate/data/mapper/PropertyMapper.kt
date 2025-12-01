package com.example.simplerealestate.data.mapper

import com.example.simplerealestate.data.model.PropertyResultDto
import com.example.simplerealestate.domain.model.Attachment
import com.example.simplerealestate.domain.model.AttachmentType
import com.example.simplerealestate.domain.model.BuyPrice
import com.example.simplerealestate.domain.model.Listing
import com.example.simplerealestate.domain.model.ListingType
import com.example.simplerealestate.domain.model.Localization
import com.example.simplerealestate.domain.model.LocalizedContent
import com.example.simplerealestate.domain.model.Prices
import com.example.simplerealestate.domain.model.Property
import com.example.simplerealestate.domain.model.PropertyAddress
import com.example.simplerealestate.domain.model.Text
import java.text.NumberFormat
import java.util.Locale

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
fun PropertyResultDto.toDomain(likedPropertyIds: Set<String> = emptySet()): Property {
    return Property(
        id = id,
        listingType = listingType?.let { ListingType(type = it.type) },
        isLiked = likedPropertyIds.contains(id),
        listing = Listing(
            offerType = listing.offerType,
            prices = Prices(
                currency = listing.prices.currency,
                buy = BuyPrice(
                    area = listing.prices.buy.area,
                    price = listing.prices.buy.price,
                    interval = listing.prices.buy.interval
                ),
                formattedPrice = formatPrice(listing.prices.currency, listing.prices.buy.price)
            ),
            address = listing.address.let { address ->
                PropertyAddress(
                    country = address.country,
                    locality = address.locality,
                    postalCode = address.postalCode,
                    region = address.region,
                    street = address.street,
                    formattedAddress = formatAddress(address.street, address.locality, address.region)
                )
            },
            localization = Localization(
                de = listing.localization.de?.let { de ->
                    val attachments = de.attachments.map {
                        Attachment(
                            type = AttachmentType.fromValue(it.type),
                            url = it.url
                        )
                    }
                    LocalizedContent(
                        attachments = attachments,
                        text = de.text?.let { Text(title = it.title) },
                        bannerImage = attachments.firstOrNull { it.type == AttachmentType.IMAGE }?.url
                    )
                }
            )
        )
    )
}

fun List<PropertyResultDto>.toDomain(likedPropertyIds: Set<String> = emptySet()): List<Property> =
    map { it.toDomain(likedPropertyIds) }

private fun formatPrice(currency: String, price: Long): String {
    val formattedNumber = NumberFormat.getNumberInstance(Locale.getDefault()).format(price)
    return "$currency $formattedNumber"
}

private fun formatAddress(street: String?, locality: String?, region: String?): String =
    listOfNotNull(street, locality, region).joinToString(", ")