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
        title: String = "Test Property",
        price: Long = 500000L,
        formattedPrice: String = "CHF 500,000",
        street: String = "Test Street 1",
        locality: String = "Zurich",
        postalCode: String = "8000",
        imageUrl: String = "https://example.com/image.jpg"
    ): Property {
        return Property(
            id = id,
            listingType = ListingType("BUY"),
            listing = Listing(
                offerType = "BUY",
                prices = Prices(
                    currency = "CHF",
                    buy = BuyPrice(
                        area = null,
                        price = price,
                        interval = null
                    ),
                    formattedPrice = formattedPrice
                ),
                address = PropertyAddress(
                    country = "CH",
                    locality = locality,
                    postalCode = postalCode,
                    region = "ZH",
                    street = street,
                    formattedAddress = "$street, $postalCode $locality"
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
            )
        )
    }
}