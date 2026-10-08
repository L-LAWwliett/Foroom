package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import com.example.foroom.helper.UiWait
import com.example.foroom.pages.CreateChatPage

object CreateChatSteps {
    fun openCreateChat() = apply {
        UiWait.enabled(CreateChatPage.navigationButton)
        onView(CreateChatPage.navigationButton).perform(click())
        UiWait.displayed(CreateChatPage.nameField)
    }
    fun enterChatName(title: String) = apply {
        UiWait.displayed(CreateChatPage.nameField)
        onView(CreateChatPage.nameField).perform(replaceText(title), closeSoftKeyboard())
    }
    fun selectSecondAvatar() = apply {
        UiWait.enabled(CreateChatPage.secondAvatar)
        onView(CreateChatPage.secondAvatar).perform(click())
    }
    fun tapCreateChat() = apply {
        UiWait.enabled(CreateChatPage.createButton)
        onView(CreateChatPage.createButton).perform(click())
    }
}
