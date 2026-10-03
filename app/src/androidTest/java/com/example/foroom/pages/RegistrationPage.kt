package com.example.foroom.pages

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.design_system.components.image_chooser.ImageChooserItemView
import com.example.design_system.components.image_chooser.ImageChooserListView
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import com.example.design_system.R as DesignR

class RegistrationPage {

    val usernameField: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.userNameInput))
    )

    val passwordField: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.passwordInput))
    )

    val repeatPasswordField: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.repeatPasswordInput))
    )

    val avatarList: Matcher<View> = withId(R.id.listView)

    val signUpButton: Matcher<View> = withId(R.id.signUpButton)
    val homeContainer: Matcher<View> = withId(R.id.homeContainer)

    val homeNavigationBar: Matcher<View> = withId(R.id.navBar)

    val secondAvatar: Matcher<View> = object : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description) {
            description.appendText(
                "second avatar in the first row, ready for selection"
            )
        }

        override fun matchesSafely(view: View): Boolean {
            if (view !is ImageChooserItemView) return false

            val row = view.parent as? ViewGroup ?: return false
            val list = row.parent as? ImageChooserListView ?: return false

            return list.id == R.id.listView &&
                    list.isChoosingEnabled &&
                    list.images.size >= 2 &&
                    list.getChildAt(0) === row &&
                    row.getChildAt(2) === view
        }
    }

    fun enterUsername(username: String) {
        onView(usernameField)
            .perform(replaceText(username), closeSoftKeyboard())
    }

    fun enterPassword(password: String) {
        onView(passwordField)
            .perform(replaceText(password), closeSoftKeyboard())
    }

    fun enterRepeatPassword(password: String) {
        onView(repeatPasswordField)
            .perform(replaceText(password), closeSoftKeyboard())
    }

    fun selectSecondAvatar() {
        onView(secondAvatar)
            .waitUntilVisible(15)
            .perform(click())
    }

    fun tapSignUp() {
        onView(signUpButton).perform(click())
    }
}