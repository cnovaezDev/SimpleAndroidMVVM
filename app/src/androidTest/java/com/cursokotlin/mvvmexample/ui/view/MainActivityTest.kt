package com.cursokotlin.mvvmexample.ui.view

import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.cursokotlin.mvvmexample.R
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Carlos A. Novaez Guerrero on 15/11/2023 18:06
 * cnovaez.dev@outlook.com
 */
//@HiltAndroidTest
//@UninstallModules(RoomModule::class, NetworkModule::class)
//@RunWith(AndroidJUnit4::class)
class MainActivityTest {

//    @get:Rule
//    val hiltRule = HiltAndroidRule(this)
//
//    private lateinit var quoteViewModel: QuoteViewModel
//
//    @Before
//    fun onBefore() {
//        hiltRule.inject()
//    }


    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun assert_that_random_quote_is_changing_when_doing_click_in_container() {
        var currText = ""
        var afterClickText = ""
        onView(withId(R.id.tvQuote)).check { view, _ ->
            val textView = view as TextView
            currText = textView.text.toString()
        }
        onView(withId(R.id.viewContainer)).perform(click())
        onView(withId(R.id.tvQuote)).check { view, _ ->
            val textView = view as TextView
            afterClickText = textView.text.toString()
        }
        assert(currText != afterClickText)
    }


}