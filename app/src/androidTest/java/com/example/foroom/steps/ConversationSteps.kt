package com.example.foroom.steps

import com.example.foroom.pages.ConversationPage

object ConversationSteps {

    fun openChat(title: String) {
        ChatSteps.openChat(title)
        ConversationPage.checkOpened(title)
    }

    fun sendMessage(text: String) {
        ConversationPage.sendMessage(text)
    }

    fun checkMessage(text: String) {
        ConversationPage.checkMessage(text)
    }

    fun checkMessageFrom(text: String, sender: String) {
        ConversationPage.checkMessageFrom(text, sender)
    }

    fun closeChat() {
        ConversationPage.closeChat()
    }

    fun reopenAndCheck(title: String, message: String) {
        closeChat()
        openChat(title)
        checkMessage(message)
    }

    fun moveGreetingIntoOlderHistory(
        greeting: String,
        suffix: String
    ) {
        repeat(25) { index ->
            sendMessage("Additional message ${index + 1} $suffix")
        }

        check(!ConversationPage.isMessageVisible(greeting)) {
            "Greeting is still visible after 25 additional messages."
        }
    }

    fun findOlderMessageFrom(
        message: String,
        sender: String,
        maxSwipes: Int = 30
    ) {
        check(!ConversationPage.isMessageVisible(message)) {
            "Greeting must start outside the visible message area."
        }

        repeat(maxSwipes) {
            ConversationPage.swipeToOlderMessages()

            if (ConversationPage.isMessageVisible(message)) {
                checkMessageFrom(message, sender)
                return
            }
        }

        throw AssertionError(
            "Message from $sender was not found after " +
                    "$maxSwipes swipes: $message"
        )
    }
}