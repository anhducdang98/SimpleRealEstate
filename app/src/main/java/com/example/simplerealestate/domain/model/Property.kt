package com.example.simplerealestate.domain.model

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
data class Property(
    val id: String,
    val listingType: ListingType?,
    val listing: Listing
)

data class ListingType(
    val type: String
)

data class Listing(
    val offerType: String,
    val prices: Prices,
    val address: PropertyAddress,
    val localization: Localization
)

data class Prices(
    val currency: String,
    val buy: BuyPrice,
    val formattedPrice: String
)

data class BuyPrice(
    val area: String?,
    val price: Long,
    val interval: String?
)

data class PropertyAddress(
    val country: String?,
    val locality: String?,
    val postalCode: String?,
    val region: String?,
    val street: String?,
    val formattedAddress: String
)

data class Localization(
    val de: LocalizedContent?
)

data class LocalizedContent(
    val attachments: List<Attachment>,
    val text: Text?,
    val bannerImage: String?
)

enum class AttachmentType(val value: String) {
    IMAGE("IMAGE"),
    UNKNOWN("UNKNOWN");

    companion object {
        fun fromValue(value: String): AttachmentType {
            return entries.find { it.value == value } ?: UNKNOWN
        }
    }
}

data class Attachment(
    val type: AttachmentType,
    val url: String
)

data class Text(
    val title: String?
)