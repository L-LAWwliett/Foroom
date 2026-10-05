package com.example.foroom.steps

import com.example.foroom.pages.CreateChatPage

object CreateChatSteps {

    fun createChat(chatName: String) {
        CreateChatPage.openCreateChat()
        CreateChatPage.enterChatName(chatName)
        CreateChatPage.selectSecondAvatar()
        CreateChatPage.createChat()
        CreateChatPage.checkChatCreated(chatName)
    }
}