package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alternator.foroom.R
import com.example.design_system.components.chat.ForoomChatCardView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import com.example.design_system.R as DesignR

object ChatsPage {
    val navigationButton: Matcher<View> = withId(R.id.homeNavigationChats)
    val list: Matcher<View> = withId(R.id.chatsRecyclerView)
    val searchField: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.searchChatInput))
    )

    private val activeView: Matcher<View> = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("attached view in the focused window")
        }
        override fun matchesSafely(view: View): Boolean =
            view.isAttachedToWindow && view.hasWindowFocus()
    }

    fun chatWithTitle(title: String): Matcher<View> = allOf(
        withId(DesignR.id.chatTitleTextView), withText(title),
        isDescendantOfA(list), isDisplayed(), activeView
    )

    fun openButton(title: String): Matcher<View> {
        val card: Matcher<View> = allOf(
            isAssignableFrom(ForoomChatCardView::class.java),
            hasDescendant(chatWithTitle(title))
        )
        return allOf(
            withId(DesignR.id.sendMessageButton),
            isDescendantOfA(card), isDisplayed(), activeView
        )
    }
}
