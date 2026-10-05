package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.LoginPage
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.isEmptyOrNullString
import org.hamcrest.Matchers.not

class LoginSteps {

    private val loginPage = LoginPage()

    fun checkLoginScreenDisplayed() {
        onView(loginPage.loginButton)
            .waitUntilVisible(15)
            .check(matches(isDisplayed()))

        onView(loginPage.usernameField)
            .check(matches(isDisplayed()))

        onView(loginPage.passwordField)
            .check(matches(isDisplayed()))

        onView(loginPage.signUpButton)
            .check(matches(isDisplayed()))
    }

    fun login(username: String, password: String) {
        loginPage.enterUsername(username)
        loginPage.enterPassword(password)
        loginPage.tapLogin()
    }

    fun openRegistration() {
        loginPage.tapSignUp()
    }

    fun checkUsernameError() {
        onView(loginPage.usernameError)
            .waitUntilVisible(15)
            .check(
                matches(
                    allOf(
                        isDisplayed(),
                        withText(not(isEmptyOrNullString()))
                    )
                )
            )
    }

    fun checkPasswordError() {
        onView(loginPage.passwordError)
            .waitUntilVisible(15)
            .check(
                matches(
                    allOf(
                        isDisplayed(),
                        withText(not(isEmptyOrNullString()))
                    )
                )
            )
    }
}