package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.Helper.waitUntilVisible

object ProfilePage {

    fun openProfile() {
        onView(withId(R.id.homeNavigationProfile))
            .perform(click())
    }

    fun openChangePassword() {
        onView(withId(R.id.changePasswordItem))
            .perform(click())
    }

    fun openChangeLanguage() {
        onView(withId(R.id.changeLanguageItem))
            .perform(click())
    }

    fun verifyGeorgianLanguageLabel() {
        onView(withText("ენის შეცვლა"))
            .check(matches(isDisplayed()))
    }

    fun verifyEnglishLanguageLabel() {
        onView(withText("Change Language"))
            .check(matches(isDisplayed()))
    }

    fun signOut() {
        onView(withId(R.id.signOutItem))
            .waitUntilVisible(15)
            .perform(click())
    }
    fun isLoginScreenDisplayed(): Boolean {
        var displayed = false

        onView(withId(R.id.logInButton))
            .check { view, noViewException ->
                displayed = noViewException == null &&
                        view != null &&
                        isDisplayed().matches(view)
            }

        return displayed
    }
}