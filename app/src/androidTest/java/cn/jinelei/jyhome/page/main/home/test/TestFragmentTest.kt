package cn.jinelei.jyhome.page.main.home.test

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import cn.jinelei.jyhome.page.main.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestFragmentTest {

    @Test
    fun testEvent() {
        val scenario = launchActivity<MainActivity>()
    }
}
    