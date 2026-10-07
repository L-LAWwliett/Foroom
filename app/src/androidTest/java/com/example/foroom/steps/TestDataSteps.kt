package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.Helper.UiWait
import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.LoginPage
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.isEmptyOrNullString
import org.hamcrest.Matchers.not

object TestDataSteps {

    const val USER_A = "user_a"
    const val USER_B = "user_b"
    const val PASSWORD = "TestPass123!"

    const val JOHN_CHAT = "johnWeek"
    const val OWN_CHAT = "Akaki Zakariadze"
    const val SHARED_CHAT = "something"

    private val loginPage = LoginPage()
    private val loginSteps = LoginSteps()
    private val registrationSteps = RegistrationSteps()

    fun prepare() {
        ensureAccount(USER_A)
        ensureAccount(USER_B)
        signInAs(USER_A)

        listOf(JOHN_CHAT, OWN_CHAT, SHARED_CHAT).forEach { title ->
            ChatsPage.searchChat(title)

            if (!ChatsPage.chatExists(title)) {
                CreateChatSteps.createChat(title)
                ConversationPageSteps.closeCreatedChat()
            }
        }

        ChatsPage.searchChat("")
    }

    fun signInAs(username: String) {
        goToLoginScreen()
        loginSteps.checkLoginScreenDisplayed()
        loginSteps.login(username, PASSWORD)

        check(awaitLoginResult() == LoginResult.SUCCESS) {
            "Could not log in as $username. Check the test password."
        }

        ChatsPage.openChats()
    }

    private fun ensureAccount(username: String) {
        goToLoginScreen()
        loginSteps.checkLoginScreenDisplayed()
        loginSteps.login(username, PASSWORD)

        when (awaitLoginResult()) {
            LoginResult.SUCCESS -> Unit

            LoginResult.MISSING_ACCOUNT -> {
                loginSteps.openRegistration()
                registrationSteps.checkRegistrationScreenDisplayed()
                registrationSteps.register(username, PASSWORD)
                registrationSteps.checkRegistrationSuccessful()
            }

            LoginResult.ERROR -> {
                throw AssertionError(
                    "Login failed for $username. " +
                            "An existing account may have another password."
                )
            }
        }

        ProfileSteps.signOut()
    }

    private fun goToLoginScreen() {
        UiWait.until("Initial application screen is ready") {
            UiWait.visible(it, withId(R.id.logInButton)) ||
                    UiWait.visible(it, withId(R.id.navBar)) ||
                    UiWait.visible(it, withId(R.id.closeButton))
        }

        var needsClosing = false

        UiWait.until("Inspect current screen") {
            needsClosing = UiWait.visible(
                it,
                withId(R.id.closeButton)
            )
            true
        }

        if (needsClosing) {
            onView(withId(R.id.closeButton)).perform(click())
        }

        UiWait.until("Login or home screen is ready") {
            UiWait.visible(it, withId(R.id.logInButton)) ||
                    UiWait.visible(it, withId(R.id.navBar))
        }

        ProfileSteps.signOut()
        loginSteps.checkLoginScreenDisplayed()
    }

    private fun awaitLoginResult(): LoginResult {
        var result = LoginResult.ERROR

        val missingAccount = allOf(
            loginPage.usernameError,
            withText("Username does not exist")
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
                UiWait.visible(root, withId(R.id.navBar)) -> {
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

// Setup calls this after CreateChatSteps opens a new conversation.
private object ConversationPageSteps {
    fun closeCreatedChat() {
        com.example.foroom.pages.ConversationPage.closeChat()
        ChatsPage.openChats()
    }
}