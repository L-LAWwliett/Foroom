package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.constants.TestConstants
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.ConversationSteps
import com.example.foroom.steps.TestDataSteps
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class ConversationTests {
    @get:Rule
    val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    @Before
    fun prepareAccountsAndChats() {
        TestDataSteps.prepare()
    }

    @Test
    fun sentMessageInJohnWeekRemainsAfterReopening() {
        val message = "${TestConstants.DRINK_MESSAGE} ${uniqueSuffix()}"
        ChatSteps.disableListAnimations().enterSearch(TestConstants.JOHN_CHAT)
            .checkChatDisplayed(TestConstants.JOHN_CHAT).tapChat(TestConstants.JOHN_CHAT)
        ConversationSteps.checkOpened(TestConstants.JOHN_CHAT)
            .enterMessage(message).tapSend().checkMessage(message).closeChat()
        ChatSteps.disableListAnimations().enterSearch(TestConstants.JOHN_CHAT)
            .checkChatDisplayed(TestConstants.JOHN_CHAT).tapChat(TestConstants.JOHN_CHAT)
        ConversationSteps.checkOpened(TestConstants.JOHN_CHAT).checkMessage(message)
    }

    @Test
    fun sendAcademyModuleQuestionInOwnChat() {
        val question = "${TestConstants.ACADEMY_QUESTION} ${uniqueSuffix()}"
        ChatSteps.disableListAnimations().enterSearch(TestConstants.OWN_CHAT)
            .checkChatDisplayed(TestConstants.OWN_CHAT).tapChat(TestConstants.OWN_CHAT)
        ConversationSteps.checkOpened(TestConstants.OWN_CHAT)
            .enterMessage(question).tapSend().checkMessage(question)
    }

    @Test
    fun anotherAccountReadsOlderGreetingAndReplies() {
        val suffix = uniqueSuffix()
        val greeting = "${TestConstants.GREETING} $suffix"
        val reply = "${TestConstants.REPLY} $suffix"
        ChatSteps.disableListAnimations().enterSearch(TestConstants.SHARED_CHAT)
            .checkChatDisplayed(TestConstants.SHARED_CHAT).tapChat(TestConstants.SHARED_CHAT)
        ConversationSteps.checkOpened(TestConstants.SHARED_CHAT)
            .enterMessage(greeting).tapSend().checkMessage(greeting)
        repeat(TestConstants.ADDITIONAL_MESSAGE_COUNT) { index ->
            val message = "${TestConstants.ADDITIONAL_MESSAGE} ${index + 1} $suffix"
            ConversationSteps.enterMessage(message).tapSend().checkMessage(message)
        }
        ConversationSteps.checkMessageNotVisible(greeting).closeChat()
        TestDataSteps.signInAs(TestConstants.USER_B)
        ChatSteps.disableListAnimations().enterSearch(TestConstants.SHARED_CHAT)
            .checkChatDisplayed(TestConstants.SHARED_CHAT).tapChat(TestConstants.SHARED_CHAT)
        ConversationSteps.checkOpened(TestConstants.SHARED_CHAT)
            .reachOlderMessage(greeting).checkMessageFrom(greeting, TestConstants.USER_A)
            .enterMessage(reply).tapSend().checkMessage(reply)
            .checkMessageFrom(reply, TestConstants.USER_B).closeChat()
        TestDataSteps.signInAs(TestConstants.USER_A)
        ChatSteps.disableListAnimations().enterSearch(TestConstants.SHARED_CHAT)
            .checkChatDisplayed(TestConstants.SHARED_CHAT).tapChat(TestConstants.SHARED_CHAT)
        ConversationSteps.checkOpened(TestConstants.SHARED_CHAT).checkMessageFrom(reply, TestConstants.USER_B)
    }

    private fun uniqueSuffix(): String = UUID.randomUUID().toString().take(TestConstants.SUFFIX_LENGTH)
}
