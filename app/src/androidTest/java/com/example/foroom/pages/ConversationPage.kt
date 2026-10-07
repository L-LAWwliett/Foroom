package com.example.foroom.pages

import android.graphics.Rect
import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.components.message.ForoomMessageView
import com.example.foroom.Helper.swiper
import com.example.foroom.Helper.UiWait
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

object ConversationPage {

    private val messageInput: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.messageInput))
    )

    private val sendButton: Matcher<View> =
        withId(R.id.sendMessageButton)

    private val messageList: Matcher<View> =
        withId(R.id.messagesRecyclerView)

    private fun messageText(text: String): Matcher<View> = allOf(
        withId(DesignR.id.messageTextView),
        withText(text),
        isDescendantOfA(messageList)
    )

    fun checkOpened(title: String) {
        UiWait.displayed(messageInput)

        val chatTitle: Matcher<View> = allOf(
            withId(DesignR.id.chatNameTextView),
            withText(title),
            isDescendantOfA(withId(R.id.chatHeaderView))
        )

        UiWait.displayed(chatTitle)
        onView(chatTitle).check(matches(isDisplayed()))
        UiWait.enabled(sendButton)
    }

    fun sendMessage(text: String) {
        UiWait.enabled(sendButton)
        UiWait.displayed(messageInput)

        onView(messageInput).perform(
            replaceText(text),
            closeSoftKeyboard()
        )

        UiWait.enabled(sendButton)
        onView(sendButton).perform(click())

        UiWait.until("Message input clears after sending") { root ->
            val input = UiWait.find(root, messageInput) as? TextView

            input != null &&
                    input.text.isNullOrEmpty() &&
                    UiWait.find(root, sendButton)?.isEnabled == true
        }

        checkMessage(text)
    }

    fun checkMessage(text: String) {
        UiWait.displayed(messageText(text))

        onView(messageText(text))
            .check(matches(isDisplayed()))
    }

    fun checkMessageFrom(text: String, sender: String) {
        val bubble: Matcher<View> = allOf(
            isAssignableFrom(ForoomMessageView::class.java),
            isDescendantOfA(messageList),
            hasDescendant(
                allOf(
                    withId(DesignR.id.messageTextView),
                    withText(text)
                )
            ),
            hasDescendant(
                allOf(
                    withId(DesignR.id.userNameTextView),
                    withText(sender)
                )
            )
        )

        val senderInBubble: Matcher<View> = allOf(
            withId(DesignR.id.userNameTextView),
            withText(sender),
            isDescendantOfA(bubble)
        )

        UiWait.displayed(bubble)
        onView(bubble).check(matches(isDisplayed()))
        onView(senderInBubble).check(matches(isDisplayed()))
    }

    fun isMessageVisible(text: String): Boolean {
        var visible = false

        UiWait.until("Inspect message visibility") {
            visible = UiWait.visible(it, messageText(text))
            true
        }

        return visible
    }

    fun closeChat() {
        UiWait.displayed(withId(R.id.closeButton))
        onView(withId(R.id.closeButton)).perform(click())
        UiWait.displayed(withId(R.id.navBar))
    }

    fun swipeToOlderMessages() {
        UiWait.displayed(messageList)

        val listRect = Rect()
        val headerRect = Rect()
        val inputRect = Rect()

        onView(messageList).check { view, exception ->
            if (exception != null) throw exception
            checkNotNull(view).getGlobalVisibleRect(listRect)
        }

        onView(withId(R.id.chatHeaderView)).check { view, exception ->
            if (exception != null) throw exception
            checkNotNull(view).getGlobalVisibleRect(headerRect)
        }

        onView(withId(R.id.messageInput)).check { view, exception ->
            if (exception != null) throw exception
            checkNotNull(view).getGlobalVisibleRect(inputRect)
        }

        val top = maxOf(listRect.top, headerRect.bottom)
        val bottom = minOf(listRect.bottom, inputRect.top)
        val height = bottom - top

        check(height > 0) {
            "No visible message area available for swiping."
        }


        val startY = top + (height * 0.20f).toInt()
        val endY = top + (height * 0.80f).toInt()

        swiper(startY, endY, 600)
    }
}