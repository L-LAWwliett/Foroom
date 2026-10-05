package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.RegistrationPage

class RegistrationSteps {

    private val registrationPage = RegistrationPage()

    fun checkRegistrationScreenDisplayed() {
        onView(registrationPage.repeatPasswordField)
            .waitUntilVisible(15)
            .check(matches(isDisplayed()))

        onView(registrationPage.usernameField)
            .check(matches(isDisplayed()))

        onView(registrationPage.passwordField)
            .check(matches(isDisplayed()))

        onView(registrationPage.avatarList)
            .check(matches(isDisplayed()))

        onView(registrationPage.signUpButton)
            .check(matches(isDisplayed()))
    }

    fun register(username: String, password: String) {
        registrationPage.enterUsername(username)
        registrationPage.enterPassword(password)
        registrationPage.enterRepeatPassword(password)
        registrationPage.selectSecondAvatar()
        registrationPage.tapSignUp()
    }

    fun checkRegistrationSuccessful() {
        onView(registrationPage.homeNavigationBar)
            .waitUntilVisible(15)
            .check(matches(isDisplayed()))

        onView(registrationPage.homeContainer)
            .check(matches(isDisplayed()))
    }
}