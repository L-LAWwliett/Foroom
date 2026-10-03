package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.RegistrationSteps
import com.example.shared.util.runtime.user_token.UserTokenRuntimeHolder
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class LoginAndRegistrationTests {

    private val loginSteps = LoginSteps()
    private val registrationSteps = RegistrationSteps()

    private val resetSessionRule = object : ExternalResource() {

        override fun before() {
            val koin = GlobalContext.get()
            val userDataStore = koin.get<ForoomUserDataStore>()
            val tokenHolder = koin.get<UserTokenRuntimeHolder>()

            runBlocking {
                val savedLanguage = userDataStore.getUserLanguage()

                userDataStore.clearUserData()

                if (savedLanguage != null) {
                    userDataStore.saveUserLanguage(savedLanguage)
                }
            }

            tokenHolder.setUserToken("")
        }
    }

    private val activityRule =
        ActivityScenarioRule(ForoomActivity::class.java)

    @get:Rule
    val rules: TestRule = RuleChain
        .outerRule(resetSessionRule)
        .around(activityRule)

    @Test
    fun validUsernameAndInvalidPassword_showsPasswordError() {
        loginSteps.checkLoginScreenDisplayed()

        loginSteps.login(
            username = EXISTING_USERNAME,
            password = WRONG_PASSWORD
        )

        loginSteps.checkPasswordError()
    }

    @Test
    fun invalidUsernameAndInvalidPassword_showsBothErrors() {
        val nonexistentUsername = uniqueUsername("missing")

        loginSteps.checkLoginScreenDisplayed()

        loginSteps.login(
            username = nonexistentUsername,
            password = WRONG_PASSWORD
        )

        loginSteps.checkUsernameError()
        loginSteps.checkPasswordError()
    }

    @Test
    fun successfulRegistration_showsHomeScreen() {
        val newUsername = uniqueUsername("qa")

        loginSteps.checkLoginScreenDisplayed()
        loginSteps.openRegistration()

        registrationSteps.checkRegistrationScreenDisplayed()

        registrationSteps.register(
            username = newUsername,
            password = VALID_PASSWORD
        )

        registrationSteps.checkRegistrationSuccessful()
    }

    private fun uniqueUsername(prefix: String): String {
        val suffix = UUID.randomUUID()
            .toString()
            .replace("-", "")
            .take(12)

        return "${prefix}_$suffix"
    }

    companion object {
        private const val EXISTING_USERNAME = "student"
        private const val WRONG_PASSWORD = "WrongPassword123!"
        private const val VALID_PASSWORD = "TestPassword123!"
    }
}