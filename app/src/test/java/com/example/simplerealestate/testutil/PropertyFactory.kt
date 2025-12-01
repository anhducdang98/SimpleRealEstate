package com.example.simplerealestate.testutil

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

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
object PropertyFactory {

    fun createProperty(
        id: String = "1",
        title: String = "Luxuriöses Einfamilienhaus mit Pool - Musterinserat",
        price: Long = 9999999L,
        formattedPrice: String = "CHF 9,999,999",
        street: String = "Musterstrasse 999",
        locality: String = "La Brévine",
        postalCode: String = "2406",
        region: String = "NE",
        imageUrl: String = "https://example.com/image.jpg",
        isLiked: Boolean = false
    ): Property {
        return Property(
            id = id,
            listingType = ListingType("BUY"),
            listing = Listing(
                offerType = "BUY",
                prices = Prices(
                    currency = "CHF",
                    buy = BuyPrice(
                        area = "ALL",
                        price = price,
                        interval = "ONETIME"
                    ),
                    formattedPrice = formattedPrice
                ),
                address = PropertyAddress(
                    country = "CH",
                    locality = locality,
                    postalCode = postalCode,
                    region = region,
                    street = street,
                    formattedAddress = "$street, $locality, $region"
                ),
                localization = Localization(
                    de = LocalizedContent(
                        attachments = listOf(
                            Attachment(
                                type = AttachmentType.IMAGE,
                                url = imageUrl
                            )
                        ),
                        text = Text(title = title),
                        bannerImage = imageUrl
                    )
                )
            ),
            isLiked = isLiked
        )
    }
}