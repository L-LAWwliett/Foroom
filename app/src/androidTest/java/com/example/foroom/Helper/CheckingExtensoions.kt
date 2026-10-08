package com.example.foroom.helper

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.viewpager.widget.ViewPager
import org.hamcrest.Matcher
import org.hamcrest.core.IsNot.not

/**
 * Check if is Not Enabled with view Matcher
 */
fun Matcher<View>.assertIfViewIsNotEnabled() {
    waitForViewVisible(2)
    onView(this).check(ViewAssertions.matches(not(ViewMatchers.isEnabled())))
}

/**
 * Check if is Not Displayed with view Matcher
 */
fun Matcher<View>.assertIfViewIsNotDisplayed() {
    waitForViewVisible(3)
    onView(this).check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
}

/**
 * Check if is Enabled with view Matcher
 */
fun Matcher<View>.assertIfViewIsEnabled() {
    waitForViewVisible(2)
    onView(this).check(ViewAssertions.matches(ViewMatchers.isEnabled()))
}

/**
 * Check if is Clickable with view Matcher
 */
fun Matcher<View>.isViewClickable(viewIndex: Int = 0): Boolean {
    return try {
        waitForViewVisible(2)
        onView(withIndex(this, viewIndex)).check(ViewAssertions.matches(ViewMatchers.isClickable()))
        true
    } catch (_: UITestingExceptions) {
        false
    }
}

/**
 * View is visible? return Boolean
 */
fun Matcher<View>.isViewDisplayed(): Boolean {

    return try {
        waitForViewVisible(3)
        onView(this).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        true
    } catch (_: Exception) {
        false
    }
}

/**
 * View is visible? return Boolean
 */
fun Matcher<View>.assertIsViewDisplayed() {
    waitForViewVisible(5)
    onView(this).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}

/**
 * View is not visible? return Boolean
 */
fun Matcher<View>.assertIsNotViewDisplayed() {
    onView(this).check(ViewAssertions.doesNotExist())
}

/**
 * scroll and View is visible? return Boolean
 */
fun Matcher<View>.assertIsViewDisplayedScrollTo() {
    waitForViewVisible(5)
    onView(this).perform(ViewActions.scrollTo()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}

/**
 * for asserting if text is visible on screen in time
 */
fun isTextOnScreen(textOnScreen: String, timeInSec: Int = 1): Boolean {

    repeat(timeInSec) {
        try {
            ViewMatchers.withText(textOnScreen).waitForViewVisible(timeInSec)
            return true
        } catch (_: UITestingExceptions) {
            Thread.sleep(1000)
        }
    }
    return false
}

/**
 * scroll RecycleView and View is visible? return Boolean
 */
fun Matcher<View>.assertIsRecyclerViewScrollTo() {
    waitForViewVisible(5)
    onView(this).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.scrollTo()))
}

/**
 * get RecycleView items count
 */
fun Matcher<View>.getRecyclerViewCount(): Int? {
    var itemCount: Int? = null
    onView(this).check { view, _ ->
        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        itemCount = adapter?.itemCount
    }
    return itemCount
}

/**
 * View is completelydisplayed? return Boolean
 */
fun Matcher<View>.isViewCompletelyDisplayed(): Boolean {

    return try {
        waitForViewVisible(2)
        onView(this).check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        true
    } catch (_: Exception) {
        false
    }
}

/**
 * get RecycleView items count
 */
fun Matcher<View>.getViewPagerCount(): Int {
    waitForViewVisible(10)
    var itemCount: Int? = null
    onView(this).check { view, _ ->
        val viewPager = view as ViewPager
        val adapter = viewPager.adapter
        itemCount = adapter?.count
    }
    return itemCount!!
}

/**
 * Check if is Not Enabled with view Matcher
 */
fun Matcher<View>.assertIfViewIsFocused(timeOutInSec: Int = 3) {
    waitForViewVisible(timeOutInSec)
    onView(this).check(ViewAssertions.matches(ViewMatchers.isFocused()))
}
