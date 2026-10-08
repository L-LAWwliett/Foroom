package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alternator.foroom.R
import com.example.design_system.components.message.ForoomMessageView
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

object ConversationPage {
    val messageInput: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.messageInput))
    )
    val sendButton: Matcher<View> = withId(R.id.sendMessageButton)
    val messageList: Matcher<View> = withId(R.id.messagesRecyclerView)
    val header: Matcher<View> = withId(R.id.chatHeaderView)
    val inputContainer: Matcher<View> = withId(R.id.messageInput)
    val closeButton: Matcher<View> = withId(R.id.closeButton)

    fun chatTitle(title: String): Matcher<View> = allOf(
        withId(DesignR.id.chatNameTextView), withText(title),
        isDescendantOfA(header)
    )

    fun messageText(text: String): Matcher<View> = allOf(
        withId(DesignR.id.messageTextView), withText(text),
        isDescendantOfA(messageList)
    )

    fun messageBubble(text: String, sender: String): Matcher<View> = allOf(
        isAssignableFrom(ForoomMessageView::class.java),
        isDescendantOfA(messageList),
        hasDescendant(allOf(withId(DesignR.id.messageTextView), withText(text))),
        hasDescendant(allOf(withId(DesignR.id.userNameTextView), withText(sender)))
    )

    fun messageSender(text: String, sender: String): Matcher<View> = allOf(
        withId(DesignR.id.userNameTextView), withText(sender),
        isDescendantOfA(messageBubble(text, sender))
    )
}
