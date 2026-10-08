package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.helper.UiWait
import com.example.foroom.pages.ProfilePage

object ProfileSteps {
    fun checkHomeDisplayed() = apply {
        UiWait.displayed(ProfilePage.homeNavigationBar)
        onView(ProfilePage.homeNavigationBar).check(matches(isDisplayed()))
    }
    fun openProfile() = apply {
        UiWait.enabled(ProfilePage.navigationButton)
        onView(ProfilePage.navigationButton).perform(click())
    }
    fun tapSignOut() = apply {
        UiWait.enabled(ProfilePage.signOutButton)
        onView(ProfilePage.signOutButton).perform(click())
    }
}
