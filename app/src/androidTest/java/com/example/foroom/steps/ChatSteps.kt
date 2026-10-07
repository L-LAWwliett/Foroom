package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.UiWait
import com.example.foroom.pages.ChatsPage

object ChatSteps {
    fun findChat(chatName: String) {
        ChatsPage.searchChat(chatName)
        val chat = ChatsPage.chatWithTitle(chatName)
        UiWait.displayed(chat)
        onView(chat).check(matches(isDisplayed()))
    }

    fun openChat(chatName: String) {
        findChat(chatName)
        ChatsPage.openChat(chatName)
    }
}
