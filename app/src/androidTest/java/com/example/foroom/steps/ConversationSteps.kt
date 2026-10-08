package com.example.foroom.steps

import android.graphics.Rect
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.constants.TestConstants
import com.example.foroom.helper.UiWait
import com.example.foroom.helper.swiper
import com.example.foroom.pages.ConversationPage
import com.example.foroom.pages.ProfilePage

object ConversationSteps {
    fun checkOpened(title: String) = apply {
        UiWait.displayed(ConversationPage.messageInput)
        val chatTitle = ConversationPage.chatTitle(title)
        UiWait.displayed(chatTitle)
        onView(chatTitle).check(matches(isDisplayed()))
        UiWait.enabled(ConversationPage.sendButton)
    }

    fun enterMessage(text: String) = apply {
        UiWait.enabled(ConversationPage.sendButton)
        UiWait.displayed(ConversationPage.messageInput)
        onView(ConversationPage.messageInput).perform(replaceText(text), closeSoftKeyboard())
    }

    fun tapSend() = apply {
        UiWait.enabled(ConversationPage.sendButton)
        onView(ConversationPage.sendButton).perform(click())
        UiWait.until("Message input clears after sending") {
            val input = UiWait.find(it, ConversationPage.messageInput) as? TextView
            input != null && input.text.isNullOrEmpty() &&
                UiWait.find(it, ConversationPage.sendButton)?.isEnabled == true
        }
    }

    fun checkMessage(text: String) = apply {
        val message = ConversationPage.messageText(text)
        UiWait.displayed(message)
        onView(message).check(matches(isDisplayed()))
    }

    fun checkMessageFrom(text: String, sender: String) = apply {
        val bubble = ConversationPage.messageBubble(text, sender)
        UiWait.displayed(bubble)
        onView(bubble).check(matches(isDisplayed()))
        onView(ConversationPage.messageSender(text, sender)).check(matches(isDisplayed()))
    }

    fun isMessageVisible(text: String): Boolean {
        var visible = false
        UiWait.until("Inspect message visibility") {
            visible = UiWait.visible(it, ConversationPage.messageText(text))
            true
        }
        return visible
    }

    fun checkMessageNotVisible(text: String) = apply {
        check(!isMessageVisible(text)) { "Message must be outside the visible area: $text" }
    }

    fun closeChat() = apply {
        UiWait.displayed(ConversationPage.closeButton)
        onView(ConversationPage.closeButton).perform(click())
        UiWait.displayed(ProfilePage.homeNavigationBar)
    }

    fun swipeToOlderMessages() = apply {
        UiWait.displayed(ConversationPage.messageList)
        val listRect = Rect()
        val headerRect = Rect()
        val inputRect = Rect()
        onView(ConversationPage.messageList).check { view, error ->
            if (error != null) throw error
            checkNotNull(view).getGlobalVisibleRect(listRect)
        }
        onView(ConversationPage.header).check { view, error ->
            if (error != null) throw error
            checkNotNull(view).getGlobalVisibleRect(headerRect)
        }
        onView(ConversationPage.inputContainer).check { view, error ->
            if (error != null) throw error
            checkNotNull(view).getGlobalVisibleRect(inputRect)
        }
        val top = maxOf(listRect.top, headerRect.bottom)
        val height = minOf(listRect.bottom, inputRect.top) - top
        check(height > 0) { "No visible message area available for swiping." }
        swiper(top + (height * TestConstants.SWIPE_START_FRACTION).toInt(),
            top + (height * TestConstants.SWIPE_END_FRACTION).toInt(),
            TestConstants.SWIPE_DURATION_MS)
    }

    fun reachOlderMessage(text: String) = apply {
        checkMessageNotVisible(text)
        var found = false
        for (attempt in 1..TestConstants.MAX_HISTORY_SWIPES) {
            swipeToOlderMessages()
            if (isMessageVisible(text)) {
                found = true
                break
            }
        }
        check(found) { "Message not found after ${TestConstants.MAX_HISTORY_SWIPES} swipes: $text" }
    }
}
