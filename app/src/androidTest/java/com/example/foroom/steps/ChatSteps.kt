package com.example.foroom.steps

import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.CreateChatPage

object ChatSteps {

    fun createChatAndFindIt(chatName: String) {
        CreateChatPage.openCreateChat()
        CreateChatPage.enterChatName(chatName)
        CreateChatPage.selectSecondAvatar()
        CreateChatPage.createChat()
        CreateChatPage.checkChatCreated(chatName)
        CreateChatPage.closeChat()

        ChatsPage.searchChat(chatName)
        ChatsPage.checkChatDisplayed(chatName)
    }
}