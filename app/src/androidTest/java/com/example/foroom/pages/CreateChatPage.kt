package com.example.foroom.pages

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.components.image_chooser.ImageChooserItemView
import com.example.design_system.components.image_chooser.ImageChooserListView
import com.example.foroom.Helper.UiWait
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import com.example.design_system.R as DesignR

object CreateChatPage {

    private fun inputInside(@IdRes parentId: Int): Matcher<View> =
        allOf(
            withId(DesignR.id.inputEditText),
            isDescendantOfA(withId(parentId))
        )

    private val secondAvatar: Matcher<View> =
        object : TypeSafeMatcher<View>() {

            override fun describeTo(description: Description) {
                description.appendText(
                    "loaded second avatar in the first row"
                )
            }

            override fun matchesSafely(view: View): Boolean {
                if (view !is ImageChooserItemView) return false

                val row = view.parent as? ViewGroup ?: return false
                val list =
                    row.parent as? ImageChooserListView ?: return false

                return list.id == R.id.chatImageChooser &&
                        list.isChoosingEnabled &&
                        list.images.size >= 2 &&
                        list.images[1].id > 0 &&
                        list.getChildAt(0) === row &&
                        row.getChildAt(2) === view
            }
        }

    fun openCreateChat() {
        val navigationButton =
            withId(R.id.homeNavigationCreateChat)

        UiWait.enabled(navigationButton)
        onView(navigationButton).perform(click())

        UiWait.displayed(inputInside(R.id.chatNameInput))
    }

    fun enterChatName(chatName: String) {
        val nameField = inputInside(R.id.chatNameInput)

        UiWait.displayed(nameField)
        onView(nameField).perform(
            replaceText(chatName),
            closeSoftKeyboard()
        )
    }

    fun selectSecondAvatar() {
        UiWait.enabled(secondAvatar)
        onView(secondAvatar).perform(click())
    }

    fun createChat() {
        val createButton = withId(R.id.createChatButton)

        UiWait.enabled(createButton)
        onView(createButton).perform(click())
    }

    fun checkChatCreated(chatName: String) {
        // This field appears only after the conversation opens.
        UiWait.displayed(inputInside(R.id.messageInput))

        val chatTitle: Matcher<View> = allOf(
            withId(DesignR.id.chatNameTextView),
            withText(chatName),
            isDescendantOfA(withId(R.id.chatHeaderView))
        )

        UiWait.displayed(chatTitle)
        onView(chatTitle).check(matches(isDisplayed()))
    }

    fun closeChat() {
        val closeButton = withId(R.id.closeButton)

        UiWait.displayed(closeButton)
        onView(closeButton).perform(click())

        UiWait.displayed(withId(R.id.navBar))
    }
}