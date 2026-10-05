package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R

object ChangeLanguagePage {

    fun selectGeorgian() {
        onView(withId(R.id.languageButtonGeo))
            .perform(click())
    }

    fun selectEnglish() {
        onView(withId(R.id.languageButtonEng))
            .perform(click())
    }
}