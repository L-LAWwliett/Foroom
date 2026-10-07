package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
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
    val activityRule =
        ActivityScenarioRule(ForoomActivity::class.java)

    @Before
    fun prepareAccountsAndChats() {
        TestDataSteps.prepare()
    }

    @Test
    fun sentMessageInJohnWeekRemainsAfterReopening() {
        val message = "let's go for a drink ${uniqueSuffix()}"

        ConversationSteps.openChat(TestDataSteps.JOHN_CHAT)
        ConversationSteps.sendMessage(message)

        ConversationSteps.reopenAndCheck(
            TestDataSteps.JOHN_CHAT,
            message
        )
    }

    @Test
    fun sendAcademyModuleQuestionInOwnChat() {
        val question =
            "Which module do you like most in the " +
                    "Automation Academy? ${uniqueSuffix()}"

        ConversationSteps.openChat(TestDataSteps.OWN_CHAT)
        ConversationSteps.sendMessage(question)
    }

    @Test
    fun anotherAccountReadsOlderGreetingAndReplies() {
        val suffix = uniqueSuffix()
        val greeting = "Hello from User A $suffix"
        val reply = "Hello back from User B $suffix"

        ConversationSteps.openChat(TestDataSteps.SHARED_CHAT)
        ConversationSteps.sendMessage(greeting)

        ConversationSteps.moveGreetingIntoOlderHistory(
            greeting,
            suffix
        )

        ConversationSteps.closeChat()
        TestDataSteps.signInAs(TestDataSteps.USER_B)

        ConversationSteps.openChat(TestDataSteps.SHARED_CHAT)

        ConversationSteps.findOlderMessageFrom(
            greeting,
            TestDataSteps.USER_A
        )

        ConversationSteps.sendMessage(reply)
        ConversationSteps.checkMessageFrom(
            reply,
            TestDataSteps.USER_B
        )

        ConversationSteps.closeChat()
        TestDataSteps.signInAs(TestDataSteps.USER_A)

        ConversationSteps.openChat(TestDataSteps.SHARED_CHAT)
        ConversationSteps.checkMessageFrom(
            reply,
            TestDataSteps.USER_B
        )
    }

    private fun uniqueSuffix(): String =
        UUID.randomUUID().toString().take(8)
}