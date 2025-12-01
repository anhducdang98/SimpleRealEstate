package com.example.simplerealestate.ui.features.propertylist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
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
fun ComposeTestRule.assertPropertyItemDisplayed(
    title: String,
    price: String,
    address: String
) {
    onNodeWithText(title).assertIsDisplayed()
    onNodeWithText(price).assertIsDisplayed()
    onNodeWithText(address).assertIsDisplayed()
}

fun createTestProperty(
    id: String = "1",
    title: String = "Test Property",
    formattedPrice: String = "CHF 500,000",
    formattedAddress: String = "Test Street 1, 8000 Zurich"
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
                    price = 500000L,
                    interval = null
                ),
                formattedPrice = formattedPrice
            ),
            address = PropertyAddress(
                country = "CH",
                locality = "Zurich",
                postalCode = "8000",
                region = "ZH",
                street = "Test Street 1",
                formattedAddress = formattedAddress
            ),
            localization = Localization(
                de = LocalizedContent(
                    attachments = listOf(
                        Attachment(
                            type = AttachmentType.IMAGE,
                            url = "https://example.com/image.jpg"
                        )
                    ),
                    text = Text(title = title),
                    bannerImage = "https://example.com/image.jpg"
                )
            )
        )
    )
}