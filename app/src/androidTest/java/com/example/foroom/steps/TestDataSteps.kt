package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.foroom.helper.UiWait
import com.example.foroom.pages.ConversationPage
import com.example.foroom.pages.ProfilePage
import com.example.foroom.constants.TestConstants
import com.example.foroom.pages.LoginPage
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.isEmptyOrNullString
import org.hamcrest.Matchers.not

object TestDataSteps {

    private val loginPage = LoginPage()
    private val loginSteps = LoginSteps()
    private val registrationSteps = RegistrationSteps()

    fun prepare() = apply {
        ensureAccount(TestConstants.USER_A)
        ensureAccount(TestConstants.USER_B)
        signInAs(TestConstants.USER_A)

        TestConstants.CHAT_TITLES.forEach { title ->
            ChatSteps.disableListAnimations().enterSearch(title)

            if (!ChatSteps.chatExists(title)) {
                CreateChatSteps.openCreateChat()
                    .enterChatName(title)
                    .selectSecondAvatar()
                    .tapCreateChat()
                ConversationSteps.checkOpened(title).closeChat()
                ChatSteps.openChats()
            }
        }

        ChatSteps.disableListAnimations().enterSearch("")
    }

    fun signInAs(username: String) = apply {
        goToLoginScreen()
        loginSteps.checkLoginScreenDisplayed()
        loginSteps.enterUsername(username).enterPassword(TestConstants.PASSWORD).tapLogin()

        check(awaitLoginResult() == LoginResult.SUCCESS) {
            "Could not log in as $username. Check the test password."
        }

        ChatSteps.openChats()
    }

    private fun ensureAccount(username: String) {
        goToLoginScreen()
        loginSteps.checkLoginScreenDisplayed()
        loginSteps.enterUsername(username).enterPassword(TestConstants.PASSWORD).tapLogin()

        when (awaitLoginResult()) {
            LoginResult.SUCCESS -> Unit

            LoginResult.MISSING_ACCOUNT -> {
                loginSteps.openRegistration()
                registrationSteps.checkRegistrationScreenDisplayed()
                registrationSteps.enterUsername(username)
                    .enterPassword(TestConstants.PASSWORD)
                    .enterRepeatPassword(TestConstants.PASSWORD)
                    .selectSecondAvatar()
                    .tapSignUp()
                registrationSteps.checkRegistrationSuccessful()
            }

            LoginResult.ERROR -> {
                throw AssertionError(
                    "Login failed for $username. " +
                            "An existing account may have another password."
                )
            }
        }

        signOutIfNeeded()
    }

    private fun goToLoginScreen() {
        UiWait.until("Initial application screen is ready") {
            UiWait.visible(it, loginPage.loginButton) ||
                    UiWait.visible(it, ProfilePage.homeNavigationBar) ||
                    UiWait.visible(it, ConversationPage.closeButton)
        }

        var needsClosing = false

        UiWait.until("Inspect current screen") {
            needsClosing = UiWait.visible(
                it,
                ConversationPage.closeButton
            )
            true
        }

        if (needsClosing) {
            ConversationSteps.closeChat()
        }

        UiWait.until("Login or home screen is ready") {
            UiWait.visible(it, loginPage.loginButton) ||
                    UiWait.visible(it, ProfilePage.homeNavigationBar)
        }

        signOutIfNeeded()
        loginSteps.checkLoginScreenDisplayed()
    }

    private fun signOutIfNeeded() {
        var loggedOut = false
        UiWait.until("Inspect login screen") {
            loggedOut = UiWait.visible(it, loginPage.loginButton)
            true
        }
        if (!loggedOut) {
            ProfileSteps.checkHomeDisplayed().openProfile().tapSignOut()
            loginSteps.checkLoginScreenDisplayed()
        }
    }

    private fun awaitLoginResult(): LoginResult {
        var result = LoginResult.ERROR

        val missingAccount = allOf(
            loginPage.usernameError,
            withText(TestConstants.MISSING_USERNAME_ERROR)
        )

        val usernameError = allOf(
            loginPage.usernameError,
            withText(not(isEmptyOrNullString()))
        )

        val passwordError = allOf(
            loginPage.passwordError,
            withText(not(isEmptyOrNullString()))
        )

        UiWait.until("Login succeeds or reports an error") { root ->
            when {
                UiWait.visible(root, ProfilePage.homeNavigationBar) -> {
                    result = LoginResult.SUCCESS
                    true
                }

                UiWait.visible(root, missingAccount) -> {
                    result = LoginResult.MISSING_ACCOUNT
                    true
                }

                UiWait.visible(root, usernameError) ||
                        UiWait.visible(root, passwordError) -> {
                    result = LoginResult.ERROR
                    true
                }

                else -> false
            }
        }

        return result
    }

    private enum class LoginResult {
        SUCCESS,
        MISSING_ACCOUNT,
        ERROR
    }
}
