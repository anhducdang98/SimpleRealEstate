package com.example.simplerealestate.data.mapper

import com.example.simplerealestate.data.model.AttachmentDto
import com.example.simplerealestate.data.model.BuyPriceDto
import com.example.simplerealestate.data.model.ListingDto
import com.example.simplerealestate.data.model.ListingTypeDto
import com.example.simplerealestate.data.model.LocalizationDto
import com.example.simplerealestate.data.model.LocalizedContentDto
import com.example.simplerealestate.data.model.PricesDto
import com.example.simplerealestate.data.model.PropertyAddressDto
import com.example.simplerealestate.data.model.PropertyResultDto
import com.example.simplerealestate.data.model.TextDto
import com.example.simplerealestate.domain.model.AttachmentType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.NumberFormat
import java.util.Locale

/**
 * @author Dang Anh Duc
 * @since 02/12/2025
 */
class PropertyMapperTest {

    @Test
    fun givenPropertyResultDto_whenToDomain_thenFormattedAddressIsCorrect() {
        // given
        val dto = createPropertyResultDto(
            street = "Musterstrasse 999",
            locality = "La Brévine",
            region = "NE"
        )

        // when
        val result = dto.toDomain()

        // then
        assertEquals("Musterstrasse 999, La Brévine, NE", result.listing.address.formattedAddress)
    }

    @Test
    fun givenPropertyResultDto_whenToDomain_thenFormattedPriceIsCorrect() {
        // given
        val dto = createPropertyResultDto(
            currency = "CHF",
            price = 9999999
        )

        // when
        val result = dto.toDomain()

        // then
        val expectedNumber = NumberFormat.getNumberInstance(Locale.getDefault()).format(9999999)
        assertEquals("CHF $expectedNumber", result.listing.prices.formattedPrice)
    }

    @Test
    fun givenPropertyResultDtoWithLikedId_whenToDomain_thenIsLikedIsTrue() {
        // given
        val dto = createPropertyResultDto(id = "104123262")
        val likedIds = setOf("104123262")

        // when
        val result = dto.toDomain(likedIds)

        // then
        assertTrue(result.isLiked)
    }

    @Test
    fun givenPropertyResultDtoWithoutLikedId_whenToDomain_thenIsLikedIsFalse() {
        // given
        val dto = createPropertyResultDto(id = "104123262")

        // when
        val result = dto.toDomain()

        // then
        assertFalse(result.isLiked)
    }

    @Test
    fun givenPropertyResultDtoWithImageAttachment_whenToDomain_thenBannerImageIsFirstImage() {
        // given
        val dto = createPropertyResultDto(
            imageUrl = "https://media2.homegate.ch/listings/heia/104123262/image/6b53db714891bfe2321cc3a6d4af76e1.jpg"
        )

        // when
        val result = dto.toDomain()

        // then
        assertEquals(
            "https://media2.homegate.ch/listings/heia/104123262/image/6b53db714891bfe2321cc3a6d4af76e1.jpg",
            result.listing.localization.de?.bannerImage
        )
        assertEquals(AttachmentType.IMAGE, result.listing.localization.de?.attachments?.first()?.type)
    }

    private fun createPropertyResultDto(
        id: String = "104123262",
        street: String = "Musterstrasse 999",
        locality: String = "La Brévine",
        region: String = "NE",
        currency: String = "CHF",
        price: Long = 9999999,
        imageUrl: String = "https://media2.homegate.ch/listings/heia/104123262/image/6b53db714891bfe2321cc3a6d4af76e1.jpg",
        title: String = "Luxuriöses Einfamilienhaus mit Pool - Musterinserat"
    ): PropertyResultDto {
        return PropertyResultDto(
            id = id,
            remoteViewing = false,
            listingType = ListingTypeDto(type = "TOP"),
            listerBranding = null,
            listing = ListingDto(
                id = id,
                offerType = "BUY",
                categories = listOf("HOUSE", "SINGLE_HOUSE"),
                prices = PricesDto(
                    currency = currency,
                    buy = BuyPriceDto(
                        area = "ALL",
                        price = price,
                        interval = "ONETIME"
                    ),
                    rent = null
                ),
                address = PropertyAddressDto(
                    country = "CH",
                    locality = locality,
                    postalCode = "2406",
                    region = region,
                    street = street,
                    geoCoordinates = null
                ),
                characteristics = null,
                localization = LocalizationDto(
                    primary = "de",
                    de = LocalizedContentDto(
                        attachments = listOf(
                            AttachmentDto(type = "IMAGE", url = imageUrl, file = "201705241056461331496.jpg")
                        ),
                        text = TextDto(title = title, description = null),
                        urls = emptyList()
                    )
                ),
                lister = null
            )
        )
    }
}