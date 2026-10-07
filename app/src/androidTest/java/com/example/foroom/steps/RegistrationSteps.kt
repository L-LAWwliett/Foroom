package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.UiWait
import com.example.foroom.pages.RegistrationPage

class RegistrationSteps {
    private val registrationPage = RegistrationPage()

    fun checkRegistrationScreenDisplayed() {
        UiWait.displayed(registrationPage.repeatPasswordField)
        UiWait.displayed(registrationPage.usernameField)
        UiWait.displayed(registrationPage.passwordField)
        UiWait.displayed(registrationPage.avatarList)
        UiWait.displayed(registrationPage.signUpButton)
    }

    fun register(username: String, password: String) {
        checkRegistrationScreenDisplayed()
        registrationPage.enterUsername(username)
        registrationPage.enterPassword(password)
        registrationPage.enterRepeatPassword(password)
        registrationPage.selectSecondAvatar()
        registrationPage.tapSignUp()
        checkRegistrationSuccessful()
    }

    fun checkRegistrationSuccessful() {
        UiWait.displayed(registrationPage.homeNavigationBar)
        UiWait.displayed(registrationPage.homeContainer)
        onView(registrationPage.homeNavigationBar).check(matches(isDisplayed()))
    }
}
