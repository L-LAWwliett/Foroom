package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.components.chat.ForoomChatCardView
import com.example.foroom.Helper.UiWait
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction

object ChatsPage {

    private val searchField: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.searchChatInput))
    )

    fun chatWithTitle(chatName: String): Matcher<View> = allOf(
        withId(DesignR.id.chatTitleTextView),
        withText(chatName),
        isDescendantOfA(withId(R.id.chatsRecyclerView)),
        isDisplayed(),
        activeView
    )

    fun openChats() {
        UiWait.displayed(withId(R.id.homeNavigationChats))

        onView(withId(R.id.homeNavigationChats))
            .perform(click())

        UiWait.displayed(searchField)
    }

    fun searchChat(chatName: String) {
        UiWait.displayed(withId(R.id.chatsRecyclerView))

        onView(withId(R.id.chatsRecyclerView))
            .perform(object : ViewAction {

                override fun getConstraints(): Matcher<View> =
                    isAssignableFrom(RecyclerView::class.java)

                override fun getDescription(): String =
                    "Finish and disable chat list item animations"

                override fun perform(
                    uiController: UiController,
                    view: View
                ) {
                    val list = view as RecyclerView

                    list.itemAnimator?.endAnimations()
                    list.itemAnimator = null

                    uiController.loopMainThreadUntilIdle()
                }
            })

        UiWait.displayed(searchField)

        onView(searchField).perform(
            replaceText(chatName),
            closeSoftKeyboard()
        )

        UiWait.until("Chat list finishes updating") { root ->
            val list = UiWait.find(
                root,
                withId(R.id.chatsRecyclerView)
            ) as? RecyclerView

            list != null &&
                    !list.isComputingLayout &&
                    !list.hasPendingAdapterUpdates()
        }
    }

    fun chatExists(chatName: String): Boolean {
        val found = UiWait.until(
            description = "Search result: $chatName",
            timeoutMs = 10_000,
            required = false
        ) {
            UiWait.visible(it, chatWithTitle(chatName))
        }

        // A loading/error screen must not be treated as a missing chat.
        UiWait.displayed(withId(R.id.chatsRecyclerView))

        return found
    }

    fun openChat(chatName: String) {
        UiWait.displayed(chatWithTitle(chatName))

        val card: Matcher<View> = allOf(
            isAssignableFrom(ForoomChatCardView::class.java),
            hasDescendant(chatWithTitle(chatName))
        )

        val openButton: Matcher<View> = allOf(
            withId(DesignR.id.sendMessageButton),
            isDescendantOfA(card),
            isDisplayed(),
            activeView
        )

        UiWait.enabled(openButton)
        onView(openButton).perform(click())
    }
    private val activeView: Matcher<View> =
        object : TypeSafeMatcher<View>() {

            override fun describeTo(description: Description) {
                description.appendText(
                    "attached view in the focused window"
                )
            }

            override fun matchesSafely(view: View): Boolean =
                view.isAttachedToWindow && view.hasWindowFocus()
        }
}
