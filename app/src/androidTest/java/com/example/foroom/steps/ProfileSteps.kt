package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.Helper.UiWait
import com.example.foroom.pages.ProfilePage

object ProfileSteps {
    fun checkHomeDisplayed() {
        val navigation = withId(R.id.navBar)
        UiWait.displayed(navigation)
        onView(navigation).check(matches(isDisplayed()))
    }

    fun signOut() {
        UiWait.until("Login or home screen is ready") {
            UiWait.visible(it, withId(R.id.logInButton)) ||
                    UiWait.visible(it, withId(R.id.navBar))
        }
        if (ProfilePage.isLoginScreenDisplayed()) return
        checkHomeDisplayed()
        ProfilePage.openProfile()
        ProfilePage.signOut()
        UiWait.displayed(withId(R.id.logInButton))
        onView(withId(R.id.logInButton)).check(matches(isDisplayed()))
    }
}
