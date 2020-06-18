package com.example.android.hilt.analytics

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.hilt.R
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.*
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBe
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class, manifest=Config.NONE)
class AnalyticsServiceImplTest {

    @get:Rule var hiltRule = HiltAndroidRule(this)

    @Inject
    @ApplicationContext lateinit var context: Context

    @Before
    fun setup() {
        // Now the above @Inject fields can be used for our test cases
        hiltRule.inject()
    }

    @Test
    fun `Validate injected components are available`() {
        val analyticsServiceImpl = AnalyticsServiceImpl(context)
        analyticsServiceImpl.context shouldNotBe null
    }

    @Test
    fun `Validate real AnalyticsServiceImpl, it tracks correctly on landing`() {
        val analyticsServiceImpl = AnalyticsServiceImpl(context)
        analyticsServiceImpl.track("Hello")

        val expectedValue = context.resources.getString(R.string.analytics_notification, "Hello")
        ShadowToast.showedToast(expectedValue) shouldBe true
    }
}
