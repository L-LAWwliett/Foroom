package com.example.foroom.steps

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.foroom.constants.TestConstants
import com.example.foroom.helper.UiWait
import com.example.foroom.pages.ChatsPage
import org.hamcrest.Matcher

object ChatSteps {
    fun openChats() = apply {
        UiWait.enabled(ChatsPage.navigationButton)
        onView(ChatsPage.navigationButton).perform(click())
        UiWait.displayed(ChatsPage.searchField)
    }

    fun disableListAnimations() = apply {
        UiWait.displayed(ChatsPage.list)
        onView(ChatsPage.list).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> = isAssignableFrom(RecyclerView::class.java)
            override fun getDescription(): String = "Finish and disable chat list item animations"
            override fun perform(uiController: UiController, view: View) {
                val list = view as RecyclerView
                list.itemAnimator?.endAnimations()
                list.itemAnimator = null
                uiController.loopMainThreadUntilIdle()
            }
        })
    }

    fun enterSearch(title: String) = apply {
        UiWait.displayed(ChatsPage.searchField)
        onView(ChatsPage.searchField).perform(replaceText(title), closeSoftKeyboard())
        UiWait.until("Chat list finishes updating") {
            val list = UiWait.find(it, ChatsPage.list) as? RecyclerView
            list != null && !list.isComputingLayout && !list.hasPendingAdapterUpdates()
        }
    }

    fun checkChatDisplayed(title: String) = apply {
        val chat = ChatsPage.chatWithTitle(title)
        UiWait.displayed(chat)
        onView(chat).check(matches(isDisplayed()))
    }

    fun tapChat(title: String) = apply {
        val button = ChatsPage.openButton(title)
        UiWait.enabled(button)
        onView(button).perform(click())
    }

    fun chatExists(title: String): Boolean {
        val found = UiWait.until("Search result: $title",
            timeoutMs = TestConstants.SEARCH_TIMEOUT_MS, required = false) {
            UiWait.visible(it, ChatsPage.chatWithTitle(title))
        }
        UiWait.displayed(ChatsPage.list)
        return found
    }
}
