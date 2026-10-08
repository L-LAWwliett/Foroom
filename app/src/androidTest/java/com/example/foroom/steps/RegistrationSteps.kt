package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.helper.UiWait
import com.example.foroom.pages.RegistrationPage

class RegistrationSteps(private val page: RegistrationPage = RegistrationPage()) {
    fun checkRegistrationScreenDisplayed() = apply {
        listOf(page.repeatPasswordField, page.usernameField, page.passwordField,
            page.avatarList, page.signUpButton).forEach {
            UiWait.displayed(it)
            onView(it).check(matches(isDisplayed()))
        }
    }
    fun enterUsername(username: String) = apply {
        UiWait.displayed(page.usernameField)
        onView(page.usernameField).perform(replaceText(username), closeSoftKeyboard())
    }
    fun enterPassword(password: String) = apply {
        UiWait.displayed(page.passwordField)
        onView(page.passwordField).perform(replaceText(password), closeSoftKeyboard())
    }
    fun enterRepeatPassword(password: String) = apply {
        UiWait.displayed(page.repeatPasswordField)
        onView(page.repeatPasswordField).perform(replaceText(password), closeSoftKeyboard())
    }
    fun selectSecondAvatar() = apply {
        UiWait.enabled(page.secondAvatar)
        onView(page.secondAvatar).perform(click())
    }
    fun tapSignUp() = apply {
        UiWait.enabled(page.signUpButton)
        onView(page.signUpButton).perform(click())
    }
    fun checkRegistrationSuccessful() = apply {
        UiWait.displayed(page.homeNavigationBar)
        UiWait.displayed(page.homeContainer)
        onView(page.homeNavigationBar).check(matches(isDisplayed()))
    }
}
