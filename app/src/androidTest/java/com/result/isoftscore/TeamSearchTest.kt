package com.result.isoftscore

import android.support.test.espresso.Espresso
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.result.isoftscore.activity.TeamSearchActivity
import com.result.isoftscore.helper.OkhttpProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TeamSearchTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(TeamSearchActivity::class.java)
    private lateinit var idlingResource: IdlingResource

    @Before
    fun setupTest() {
        idlingResource = OkHttp3IdlingResource.create("okhttp", OkhttpProvider.getInstance())
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @Test
    fun testTeamSearchBehavior() {
        Espresso.onView(ViewMatchers.withId(android.support.design.R.id.search_src_text)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(android.support.design.R.id.search_src_text)).perform(ViewActions.typeText("Arsenal"))

        Espresso.onView(ViewMatchers.withId(R.id.recyclerview_search)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview_search)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        Espresso.onView(ViewMatchers.withText(R.string.player)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.recyclerview_list)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, ViewActions.click()))

        Espresso.pressBack()

        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(R.string.add_to_fav)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.pressBack()

        Espresso.onView(ViewMatchers.withId(R.id.recyclerview_search)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview_search)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(R.string.remove_from_fav)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @After
    fun finishTest() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}