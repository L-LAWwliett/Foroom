package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.R as DesignSystemR
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Matchers.allOf

object ChatsPage {

    fun searchChat(chatName: String) {
        onView(
            allOf(
                withId(DesignSystemR.id.inputEditText),
                isDescendantOfA(withId(R.id.searchChatInput))
            )
        )
            .waitUntilVisible(15)
            .perform(
                replaceText(chatName),
                closeSoftKeyboard()
            )
    }

    fun checkChatDisplayed(chatName: String) {
        onView(
            allOf(
                withId(DesignSystemR.id.chatTitleTextView),
                withText(chatName),
                isDescendantOfA(withId(R.id.chatsRecyclerView))
            )
        )
            .waitUntilVisible(15)
            .check(matches(isDisplayed()))
    }
}