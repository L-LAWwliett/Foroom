package com.example.foroom.pages

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignSystemR

object ChangePasswordPage {

    private fun inputInside(@IdRes parentId: Int): Matcher<View> {
        return allOf(
            withId(com.example.design_system.R.id.inputEditText),
            isDescendantOfA(withId(parentId))
        )
    }

    fun enterPassword(password: String) {
        onView(inputInside(R.id.passwordInput))
            .perform(
                replaceText(password),
                closeSoftKeyboard()
            )
    }

    fun repeatPassword(password: String) {
        onView(inputInside(R.id.repeatPasswordInput))
            .perform(
                replaceText(password),
                closeSoftKeyboard()
            )
    }

    fun confirm() {
        onView(withId(DesignSystemR.id.actionButton))
            .perform(click())
    }
}