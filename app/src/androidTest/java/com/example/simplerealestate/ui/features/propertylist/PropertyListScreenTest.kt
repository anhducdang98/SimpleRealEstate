package com.example.simplerealestate.ui.features.propertylist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.simplerealestate.ui.theme.SimpleRealEstateTheme
import org.junit.Rule
import org.junit.Test

/**
 * @author Dang Anh Duc
 * @since 01/12/2025
 */
class PropertyListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenInitialState_whenScreenDisplayed_thenShowNothing() {
        // given
        val uiState = PropertyListUiState.Initial

        // when
        composeTestRule.setContent {
            SimpleRealEstateTheme {
                PropertyListContent(
                    uiState = uiState,
                    onRetryClick = {}
                )
            }
        }

        // then
        composeTestRule.onNodeWithTag(PropertyListTestTags.LOADING_INDICATOR).assertDoesNotExist()
        composeTestRule.onNodeWithTag(PropertyListTestTags.ERROR_CONTENT).assertDoesNotExist()
        composeTestRule.onNodeWithTag(PropertyListTestTags.PROPERTY_LIST).assertDoesNotExist()
    }

    @Test
    fun givenLoadingState_whenScreenDisplayed_thenShowLoadingIndicator() {
        // given
        val uiState = PropertyListUiState.Loading

        // when
        composeTestRule.setContent {
            SimpleRealEstateTheme {
                PropertyListContent(
                    uiState = uiState,
                    onRetryClick = {}
                )
            }
        }

        // then
        composeTestRule.onNodeWithTag(PropertyListTestTags.LOADING_INDICATOR).assertIsDisplayed()
    }

    @Test
    fun givenErrorState_whenScreenDisplayed_thenShowErrorMessageAndRetryButton() {
        // given
        val uiState = PropertyListUiState.Error

        // when
        composeTestRule.setContent {
            SimpleRealEstateTheme {
                PropertyListContent(
                    uiState = uiState,
                    onRetryClick = {}
                )
            }
        }

        // then
        composeTestRule.onNodeWithTag(PropertyListTestTags.ERROR_CONTENT).assertIsDisplayed()
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun givenErrorState_whenRetryClicked_thenOnRetryClickCalled() {
        // given
        var retryClicked = false
        val uiState = PropertyListUiState.Error

        // when
        composeTestRule.setContent {
            SimpleRealEstateTheme {
                PropertyListContent(
                    uiState = uiState,
                    onRetryClick = { retryClicked = true }
                )
            }
        }
        composeTestRule.onNodeWithText("Retry").performClick()

        // then
        assert(retryClicked)
    }

    @Test
    fun givenSuccessState_whenScreenDisplayed_thenShowPropertyListWithItems() {
        // given
        val properties = listOf(
            createTestProperty(
                id = "1",
                title = "Luxury Apartment",
                formattedPrice = "CHF 750,000",
                formattedAddress = "Main Street 10, 8000 Zurich"
            ),
            createTestProperty(
                id = "2",
                title = "Modern Villa",
                formattedPrice = "CHF 1,200,000",
                formattedAddress = "Lake Road 5, 6000 Lucerne"
            )
        )
        val uiState = PropertyListUiState.Success(properties)

        // when
        composeTestRule.setContent {
            SimpleRealEstateTheme {
                PropertyListContent(
                    uiState = uiState,
                    onRetryClick = {}
                )
            }
        }

        // then
        composeTestRule.onNodeWithTag(PropertyListTestTags.PROPERTY_LIST).assertIsDisplayed()
        composeTestRule.assertPropertyItemDisplayed(
            title = "Luxury Apartment",
            price = "CHF 750,000",
            address = "Main Street 10, 8000 Zurich"
        )
        composeTestRule.assertPropertyItemDisplayed(
            title = "Modern Villa",
            price = "CHF 1,200,000",
            address = "Lake Road 5, 6000 Lucerne"
        )
    }

    @Test
    fun givenSuccessStateWithEmptyList_whenScreenDisplayed_thenShowEmptyPropertyList() {
        // given
        val uiState = PropertyListUiState.Success(emptyList())

        // when
        composeTestRule.setContent {
            SimpleRealEstateTheme {
                PropertyListContent(
                    uiState = uiState,
                    onRetryClick = {}
                )
            }
        }

        // then
        composeTestRule.onNodeWithTag(PropertyListTestTags.PROPERTY_LIST).assertIsDisplayed()
    }
}