package co.za.immedia.superhero

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest

class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = IntentsTestRule(MainActivity::class.java)

    @get:Rule
    val searchActivity = ActivityTestRule(SearchActivity::class.java)

    @Test
    fun goToSearch() {
        onView(withId(R.id.onSearchButton)).perform(click())
        intended(hasComponent(SearchActivity::class.java.name))
    }

    @Test
    fun goToCompare() {
        onView(withId(R.id.compareBtn)).perform(click())
        intended(hasComponent(CompareActivity::class.java.name))
    }

    @Test
    fun onSearchTest() {
        onView(withId(R.id.onSearchButton)).perform(click())
        // Type text and then press the button.
        onView(withId(R.id.searchbar))
            .perform(ViewActions.typeText("batman"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.searchBtn)).perform(click())
        // Check that the text was changed.
        assert(searchActivity.activity.ListHero.size > 0)
    }



}
