package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.Helper.UiWait

object ProfilePage {
    fun openProfile() {
        val button = withId(R.id.homeNavigationProfile)
        UiWait.enabled(button)
        onView(button).perform(click())
    }

    fun signOut() {
        val button = withId(R.id.signOutItem)
        UiWait.enabled(button)
        onView(button).perform(click())
    }

    fun isLoginScreenDisplayed(): Boolean {
        var displayed = false
        UiWait.until("Inspect login screen") {
            displayed = UiWait.visible(it, withId(R.id.logInButton))
            true
        }
        return displayed
    }
}
