package com.result.isoftscore

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.result.isoftscore.activity.MatchSearchActivity
import com.result.isoftscore.helper.OkhttpProvider
import org.jetbrains.anko.matchParent
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MatchSearchTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MatchSearchActivity::class.java)
    private lateinit var idlingResource: IdlingResource

    @Before
    fun setupTest() {
        idlingResource = OkHttp3IdlingResource.create("okhttp", OkhttpProvider.getInstance())
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @Test
    fun testMatchSearchBehavior() {
        onView(withId(android.support.design.R.id.search_src_text)).check(matches(isDisplayed()))
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("Cardiff"))

        onView(withId(R.id.recyclerview_search)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerview_search)).perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))


        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        onView(ViewMatchers.withText(R.string.add_to_fav)).check(matches(isDisplayed()))

        Espresso.pressBack()

        onView(withId(R.id.recyclerview_search)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerview_search)).perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        //onView(ViewMatchers.withText(R.string.remove_from_fav)).check(matches(isDisplayed()))
    }

    @After
    fun finishTest() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }


}