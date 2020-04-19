package co.za.immedia.superhero

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest

class SearchAcitivityInstrumentalTest {

    @get:Rule
    val searchActivity = ActivityTestRule(SearchActivity::class.java)
    val stringToBeTyped = "Batman"

    @Test
    fun onSearchTest() {
        // Type text and then press the button.
        onView(withId(R.id.searchbar))
            .perform(typeText(stringToBeTyped), closeSoftKeyboard())
        onView(withId(R.id.searchBtn)).perform(click())

        assert(searchActivity.activity.ListHero.size > 0)

        onView(withId(R.id.GridListView)).check(matches(isDisplayed()))

        //onView(withId(R.id.GridListView)).check(matches(hasMinimumChildCount(0)))
    }

}