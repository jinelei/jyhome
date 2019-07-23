package cn.jinelei.jyhome.page.main.home

import androidx.lifecycle.ViewModel
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import cn.jinelei.jyhome.page.main.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun testEvent() {
        val scenario = launchActivity<MainActivity>()
//        onView(withId(R.id.btn_success)).perform(click())
//                .check(onView(withId(R.id.pb_loading)).check(matches(isDisplayed()))
    }
}