package com.example.simplerealestate.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
@Serializable
data class PropertiesResponseDto(
    @SerialName("from") val from: Int,
    @SerialName("size") val size: Int,
    @SerialName("total") val total: Int,
    @SerialName("results") val results: List<PropertyResultDto>,
    @SerialName("maxFrom") val maxFrom: Int
)

@Serializable
data class PropertyResultDto(
    @SerialName("id") val id: String,
    @SerialName("remoteViewing") val remoteViewing: Boolean = false,
    @SerialName("listingType") val listingType: ListingTypeDto? = null,
    @SerialName("listerBranding") val listerBranding: ListerBrandingDto? = null,
    @SerialName("listing") val listing: ListingDto
)

@Serializable
data class ListingTypeDto(
    @SerialName("type") val type: String
)

@Serializable
data class ListerBrandingDto(
    @SerialName("logoUrl") val logoUrl: String,
    @SerialName("legalName") val legalName: String,
    @SerialName("name") val name: String? = null,
    @SerialName("address") val address: BrandingAddressDto? = null,
    @SerialName("adActive") val adActive: Boolean = false,
    @SerialName("isQualityPartner") val isQualityPartner: Boolean = false,
    @SerialName("isPremiumBranding") val isPremiumBranding: Boolean = false,
    @SerialName("profilePageUrlKeyword") val profilePageUrlKeyword: String? = null
)

@Serializable
data class BrandingAddressDto(
    @SerialName("locality") val locality: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("region") val region: String? = null,
    @SerialName("street") val street: String? = null,
    @SerialName("postalCode") val postalCode: String? = null
)

@Serializable
data class ListingDto(
    @SerialName("id") val id: String,
    @SerialName("offerType") val offerType: String,
    @SerialName("categories") val categories: List<String> = emptyList(),
    @SerialName("prices") val prices: PricesDto,
    @SerialName("address") val address: PropertyAddressDto,
    @SerialName("characteristics") val characteristics: CharacteristicsDto? = null,
    @SerialName("localization") val localization: LocalizationDto,
    @SerialName("lister") val lister: ListerDto? = null
)

@Serializable
data class CharacteristicsDto(
    @SerialName("numberOfRooms") val numberOfRooms: Double? = null,
    @SerialName("livingSpace") val livingSpace: Int? = null,
    @SerialName("lotSize") val lotSize: Int? = null,
    @SerialName("totalFloorSpace") val totalFloorSpace: Int? = null
)

@Serializable
data class PricesDto(
    @SerialName("currency") val currency: String,
    @SerialName("buy") val buy: BuyPriceDto,
    @SerialName("rent") val rent: RentPriceDto? = null
)

@Serializable
data class BuyPriceDto(
    @SerialName("area") val area: String? = null,
    @SerialName("price") val price: Long,
    @SerialName("interval") val interval: String? = null
)

@Serializable
data class RentPriceDto(
    @SerialName("gross") val gross: Long? = null,
    @SerialName("net") val net: Long? = null,
    @SerialName("interval") val interval: String? = null
)

@Serializable
data class PropertyAddressDto(
    @SerialName("country") val country: String? = null,
    @SerialName("locality") val locality: String? = null,
    @SerialName("postalCode") val postalCode: String? = null,
    @SerialName("region") val region: String? = null,
    @SerialName("street") val street: String? = null,
    @SerialName("geoCoordinates") val geoCoordinates: GeoCoordinatesDto? = null
)

@Serializable
data class GeoCoordinatesDto(
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double
)

@Serializable
data class LocalizationDto(
    @SerialName("primary") val primary: String? = null,
    @SerialName("de") val de: LocalizedContentDto? = null
)

@Serializable
data class LocalizedContentDto(
    @SerialName("attachments") val attachments: List<AttachmentDto> = emptyList(),
    @SerialName("text") val text: TextDto? = null,
    @SerialName("urls") val urls: List<UrlDto> = emptyList()
)

@Serializable
data class AttachmentDto(
    @SerialName("type") val type: String,
    @SerialName("url") val url: String,
    @SerialName("file") val file: String? = null
)

@Serializable
data class TextDto(
    @SerialName("title") val title: String? = null,
    @SerialName("description") val description: String? = null
)

@Serializable
data class UrlDto(
    @SerialName("type") val type: String? = null,
    @SerialName("href") val href: String? = null
)

@Serializable
data class ListerDto(
    @SerialName("phone") val phone: String? = null,
    @SerialName("logoUrl") val logoUrl: String? = null
)