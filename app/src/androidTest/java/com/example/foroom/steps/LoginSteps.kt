package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.foroom.helper.UiWait
import com.example.foroom.pages.LoginPage
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.isEmptyOrNullString
import org.hamcrest.Matchers.not

class LoginSteps(private val page: LoginPage = LoginPage()) {
    fun checkLoginScreenDisplayed() = apply {
        listOf(page.loginButton, page.usernameField, page.passwordField, page.signUpButton)
            .forEach { UiWait.displayed(it); onView(it).check(matches(isDisplayed())) }
    }
    fun enterUsername(username: String) = apply {
        UiWait.displayed(page.usernameField)
        onView(page.usernameField).perform(replaceText(username), closeSoftKeyboard())
    }
    fun enterPassword(password: String) = apply {
        UiWait.displayed(page.passwordField)
        onView(page.passwordField).perform(replaceText(password), closeSoftKeyboard())
    }
    fun tapLogin() = apply {
        UiWait.enabled(page.loginButton)
        onView(page.loginButton).perform(click())
    }
    fun openRegistration() = apply {
        UiWait.enabled(page.signUpButton)
        onView(page.signUpButton).perform(click())
    }
    fun checkUsernameError() = apply {
        val error = allOf(page.usernameError, withText(not(isEmptyOrNullString())))
        UiWait.displayed(error)
        onView(error).check(matches(isDisplayed()))
    }
    fun checkPasswordError() = apply {
        val error = allOf(page.passwordError, withText(not(isEmptyOrNullString())))
        UiWait.displayed(error)
        onView(error).check(matches(isDisplayed()))
    }
}
