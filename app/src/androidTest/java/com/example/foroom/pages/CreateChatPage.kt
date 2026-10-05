package com.example.foroom.pages

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.design_system.R as DesignSystemR
import com.example.design_system.components.image_chooser.ImageChooserItemView
import com.example.design_system.components.image_chooser.ImageChooserListView
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText



object CreateChatPage {

    private fun inputInside(@IdRes parentId: Int): Matcher<View> =
        allOf(
            withId(DesignSystemR.id.inputEditText),
            isDescendantOfA(withId(parentId))
        )

    private val secondAvatar: Matcher<View> =
        object : TypeSafeMatcher<View>() {

            override fun describeTo(description: Description) {
                description.appendText(
                    "second avatar in the first row, ready for selection"
                )
            }

            override fun matchesSafely(view: View): Boolean {
                if (view !is ImageChooserItemView) return false

                val row = view.parent as? ViewGroup ?: return false
                val list = row.parent as? ImageChooserListView ?: return false

                return list.id == R.id.chatImageChooser &&
                        list.isChoosingEnabled &&
                        list.images.size >= 2 &&
                        list.getChildAt(0) === row &&
                        row.getChildAt(2) === view
            }
        }

    fun enterChatName(chatName: String) {
        onView(inputInside(R.id.chatNameInput))
            .perform(
                replaceText(chatName),
                closeSoftKeyboard()
            )
    }

    fun selectSecondAvatar() {
        onView(secondAvatar)
            .waitUntilVisible(15)
            .perform(click())
    }

    fun createChat() {
        onView(withId(R.id.createChatButton))
            .perform(click())
    }

    fun checkChatCreated(chatName: String) {
        onView(
            allOf(
                withId(DesignSystemR.id.chatNameTextView),
                isDescendantOfA(withId(R.id.chatHeaderView))
            )
        )
            .waitUntilVisible(15)
            .check(matches(withText(chatName)))


    }

    fun openCreateChat() {
        onView(withId(R.id.homeNavigationCreateChat))
            .waitUntilVisible(15)
            .perform(click())

        onView(withId(R.id.chatNameInput))
            .waitUntilVisible(15)
    }

    fun closeChat() {
        onView(withId(R.id.closeButton))
            .waitUntilVisible(15)
            .perform(click())
    }
}