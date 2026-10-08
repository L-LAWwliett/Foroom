package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import org.hamcrest.Matcher

object ProfilePage {
    val navigationButton: Matcher<View> = withId(R.id.homeNavigationProfile)
    val signOutButton: Matcher<View> = withId(R.id.signOutItem)
    val homeNavigationBar: Matcher<View> = withId(R.id.navBar)
}
