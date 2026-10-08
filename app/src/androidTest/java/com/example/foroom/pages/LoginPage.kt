package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

class LoginPage {

    val usernameField: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.userNameInput))
    )

    val passwordField: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.passwordInput))
    )

    val loginButton: Matcher<View> = withId(R.id.logInButton)

    val signUpButton: Matcher<View> = withId(R.id.signUpButton)

    val usernameError: Matcher<View> = allOf(
        withId(DesignR.id.descriptionTextView),
        isDescendantOfA(withId(R.id.userNameInput))
    )

    val passwordError: Matcher<View> = allOf(
        withId(DesignR.id.descriptionTextView),
        isDescendantOfA(withId(R.id.passwordInput))
    )



}
