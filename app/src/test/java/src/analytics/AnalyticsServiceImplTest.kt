package src.analytics

import analytics.AnalyticsServiceImpl
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.hilt.R
import dagger.hilt.android.testing.*
import org.amshove.kluent.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class, manifest=Config.NONE)
class AnalyticsServiceImplTest {

    @get:Rule var hiltRule = HiltAndroidRule(this)
    private val appContext = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun `Validate real AnalyticsServiceImpl, it tracks correctly on landing`() {
        val analyticsServiceImpl = AnalyticsServiceImpl(appContext)
        analyticsServiceImpl.track("Hello")

        val expectedValue = appContext.resources.getString(R.string.analytics_notification, "Hello")
        ShadowToast.showedToast(expectedValue) shouldBe true
    }
}
