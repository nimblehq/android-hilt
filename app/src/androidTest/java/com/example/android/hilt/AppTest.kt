/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.hilt

import android.content.pm.ActivityInfo
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.hilt.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class AppTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun validateTheHappyPath() {
        ActivityScenario.launch(MainActivity::class.java)

        // Check Buttons fragment screen is displayed
        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()))

        // Tap on Button 1
        onView(withId(R.id.btMemory1)).perform(click())

        // Navigate to Logs screen
        onView(withId(R.id.btAllLogsInMem)).perform(click())

        // Check Logs fragment screen is displayed
        onView(withText(containsString("Tapped on 'Button 1'")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun validateRotationScenario() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        // Tap on Button 3
        onView(withId(R.id.btMemory3)).perform(click())

        // Navigate to Logs screen
        onView(withId(R.id.btAllLogsInMem)).perform(click())

        // Perform rotate and reverse
        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        // Check Logs fragment screen is STILL displaying the correct content
        onView(withText(containsString("Tapped on 'Button 3'")))
            .check(matches(isDisplayed()))
    }
}
