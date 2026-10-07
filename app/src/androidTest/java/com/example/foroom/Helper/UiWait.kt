package com.example.foroom.Helper


import android.os.SystemClock
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Matcher

object UiWait {

    fun find(root: View, matcher: Matcher<View>): View? =
        TreeIterables.breadthFirstViewTraversal(root)
            .firstOrNull {
                matcher.matches(it) && isDisplayed().matches(it)
            }

    fun visible(root: View, matcher: Matcher<View>): Boolean =
        find(root, matcher) != null

    fun until(
        description: String,
        timeoutMs: Long = 15_000,
        required: Boolean = true,
        condition: (View) -> Boolean
    ): Boolean {
        var satisfied = false

        onView(isRoot()).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()

            override fun getDescription(): String = description

            override fun perform(
                uiController: UiController,
                view: View
            ) {
                val deadline = SystemClock.uptimeMillis() + timeoutMs

                do {
                    uiController.loopMainThreadUntilIdle()

                    if (condition(view)) {
                        satisfied = true
                        return
                    }

                    uiController.loopMainThreadForAtLeast(100)
                } while (SystemClock.uptimeMillis() < deadline)
            }
        })

        if (required && !satisfied) {
            throw AssertionError(
                "Timed out after $timeoutMs ms: $description"
            )
        }

        return satisfied
    }

    fun displayed(matcher: Matcher<View>) {
        until("View is displayed: $matcher") {
            visible(it, matcher)
        }
    }

    fun enabled(matcher: Matcher<View>) {
        until("View is displayed and enabled: $matcher") {
            find(it, matcher)?.isEnabled == true
        }
    }
}