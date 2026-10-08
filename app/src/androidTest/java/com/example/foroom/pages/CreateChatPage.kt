package com.example.foroom.pages

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.components.image_chooser.ImageChooserItemView
import com.example.design_system.components.image_chooser.ImageChooserListView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import com.example.design_system.R as DesignR

object CreateChatPage {

    private fun inputInside(@IdRes parentId: Int): Matcher<View> =
        allOf(
            withId(DesignR.id.inputEditText),
            isDescendantOfA(withId(parentId))
        )

    val secondAvatar: Matcher<View> =
        object : TypeSafeMatcher<View>() {

            override fun describeTo(description: Description) {
                description.appendText(
                    "loaded second avatar in the first row"
                )
            }

            override fun matchesSafely(view: View): Boolean {
                if (view !is ImageChooserItemView) return false

                val row = view.parent as? ViewGroup ?: return false
                val list =
                    row.parent as? ImageChooserListView ?: return false

                return list.id == R.id.chatImageChooser &&
                        list.isChoosingEnabled &&
                        list.images.size >= 2 &&
                        list.images[1].id > 0 &&
                        list.getChildAt(0) === row &&
                        row.getChildAt(2) === view
            }
        }

    val navigationButton: Matcher<View> = withId(R.id.homeNavigationCreateChat)
    val nameField: Matcher<View> = inputInside(R.id.chatNameInput)
    val createButton: Matcher<View> = withId(R.id.createChatButton)
}
