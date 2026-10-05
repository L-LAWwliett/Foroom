package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.ChangePasswordPage
import com.example.foroom.pages.ProfilePage
import com.example.foroom.pages.ChangeLanguagePage
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import org.hamcrest.Matchers.allOf

object ProfileSteps {

    fun checkHomeDisplayed() {
        onView(withId(R.id.navBar))
            .waitUntilVisible(15)
            .check(matches(isDisplayed()))
    }

    fun changePassword(newPassword: String) {
        ProfilePage.openProfile()

        onView(withId(R.id.changePasswordItem))
            .waitUntilVisible(15)

        ProfilePage.openChangePassword()

        onView(withId(R.id.passwordInput))
            .waitUntilVisible(15)

        ChangePasswordPage.enterPassword(newPassword)
        ChangePasswordPage.repeatPassword(newPassword)
        ChangePasswordPage.confirm()
    }
    fun signOut() {
        if (ProfilePage.isLoginScreenDisplayed()) return

        checkHomeDisplayed()
        ProfilePage.openProfile()
        ProfilePage.signOut()
    }

    fun changeLanguageFromGeorgianToEnglishAndBack() {
        checkHomeDisplayed()
        ProfilePage.openProfile()

        onView(withId(R.id.changeLanguageItem))
            .waitUntilVisible(15)

        ProfilePage.openChangeLanguage()

        onView(withId(R.id.languageButtonGeo))
            .waitUntilVisible(15)

        ChangeLanguagePage.selectGeorgian()
        checkLanguageLabel("ენის შეცვლა")

        ProfilePage.openChangeLanguage()

        onView(withId(R.id.languageButtonEng))
            .waitUntilVisible(15)

        ChangeLanguagePage.selectEnglish()
        checkLanguageLabel("Change Language")

        ProfilePage.openChangeLanguage()

        onView(withId(R.id.languageButtonGeo))
            .waitUntilVisible(15)

        ChangeLanguagePage.selectGeorgian()
        checkLanguageLabel("ენის შეცვლა")
    }

    private fun checkLanguageLabel(text: String) {
        onView(
            allOf(
                withText(text),
                isDescendantOfA(withId(R.id.changeLanguageItem))
            )
        )
            .waitUntilVisible(15)
            .check(matches(isDisplayed()))
    }

}