package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.foroom.Helper.UiWait
import com.example.foroom.pages.LoginPage
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.isEmptyOrNullString
import org.hamcrest.Matchers.not

class LoginSteps {
    private val loginPage = LoginPage()

    fun checkLoginScreenDisplayed() {
        UiWait.displayed(loginPage.loginButton)
        UiWait.displayed(loginPage.usernameField)
        UiWait.displayed(loginPage.passwordField)
        UiWait.displayed(loginPage.signUpButton)
    }

    fun login(username: String, password: String) {
        checkLoginScreenDisplayed()
        loginPage.enterUsername(username)
        loginPage.enterPassword(password)
        UiWait.enabled(loginPage.loginButton)
        loginPage.tapLogin()
    }

    fun openRegistration() {
        UiWait.enabled(loginPage.signUpButton)
        loginPage.tapSignUp()
    }

    fun checkUsernameError() {
        val error = allOf(loginPage.usernameError, withText(not(isEmptyOrNullString())))
        UiWait.displayed(error)
        onView(error).check(matches(isDisplayed()))
    }

    fun checkPasswordError() {
        val error = allOf(loginPage.passwordError, withText(not(isEmptyOrNullString())))
        UiWait.displayed(error)
        onView(error).check(matches(isDisplayed()))
    }
}
