package com.example.foroom.tests

import androidx.test.espresso.Espresso.onView
import com.example.foroom.steps.ProfileSteps
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alternator.foroom.R
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.LoginSteps
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileAndChatTests {

    @get:Rule
    val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    private val loginSteps = LoginSteps()

    private val username = "username"
    private val password = "password"

    @Test
    fun createChatAndFindItInChatList() {
        loginSteps.checkLoginScreenDisplayed()
        loginSteps.login(username, password)

        onView(withId(R.id.navBar))
            .waitUntilVisible(15)
            .check(matches(isDisplayed()))

        val chatName = "Akaki Zakariadze ${System.currentTimeMillis()}"

        ChatSteps.createChatAndFindIt(chatName)
    }

    @Test
    fun changePasswordAndVerifyIt() {
        val newPassword = "AkakiTest123!"

        // შესვლა მიმდინარე პაროლით.
        loginSteps.checkLoginScreenDisplayed()
        loginSteps.login(username, password)
        ProfileSteps.checkHomeDisplayed()

        // პაროლის შეცვლა და ავტომატური დაბრუნების შემოწმება.
        ProfileSteps.changePassword(newPassword)
        loginSteps.checkLoginScreenDisplayed()

        // ახალი პაროლით წარმატებული შესვლა ადასტურებს ცვლილებას.
        loginSteps.login(username, newPassword)
        ProfileSteps.checkHomeDisplayed()

        // საწყისი პაროლის აღდგენა შემდეგი გაშვებისთვის.
        ProfileSteps.changePassword(password)
        loginSteps.checkLoginScreenDisplayed()

        loginSteps.login(username, password)
        ProfileSteps.checkHomeDisplayed()
    }

    @Test
    fun changeLanguageFromGeorgianToEnglishAndBack() {
        loginSteps.checkLoginScreenDisplayed()
        loginSteps.login(username, password)
        ProfileSteps.checkHomeDisplayed()

        ProfileSteps.changeLanguageFromGeorgianToEnglishAndBack()
    }
    @After
    fun tearDown() {
        ProfileSteps.signOut()
        loginSteps.checkLoginScreenDisplayed()
    }
}