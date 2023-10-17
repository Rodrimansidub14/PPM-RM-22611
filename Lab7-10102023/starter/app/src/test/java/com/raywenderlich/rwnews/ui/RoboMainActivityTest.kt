package com.raywenderlich.rwnews.ui
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.raywenderlich.rwnews.di.ActivityModule
import com.raywenderlich.rwnews.ui.navigation.NavigationHelper
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import fakes.FakeNavigationHelper
import com.google.common.truth.Truth.assertThat
import com.raywenderlich.rwnews.ui.list.NewsListFragment
import com.raywenderlich.rwnews.R

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@UninstallModules(ActivityModule::class)
@HiltAndroidTest // 1
@Config(application = HiltTestApplication::class) // 2
@RunWith(RobolectricTestRunner::class) // 3
@LooperMode(LooperMode.Mode.PAUSED)  // 4
class RoboMainActivityTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this) // 5

    @get:Rule(order = 1) // 2
    var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java) // 1
    @Before
    fun setUp() {
        hiltAndroidRule.inject() // 6
    }

    @Test
    fun whenMainActivityLaunchedNavigationHelperIsInvokedForFragment() {
        activityScenarioRule.scenario // 1
        val fakeHelper = navigator as FakeNavigationHelper // 2
        with(fakeHelper.replaceRequests[0]) { // 3
            assertThat(anchorId)
                .isEqualTo(R.id.anchor)
            assertThat(fragment)
                .isInstanceOf(NewsListFragment::class.java)
            assertThat(backStack)
                .isNull()
        }
    }
    @BindValue // 1
    @JvmField // 2
    val navigator: NavigationHelper = FakeNavigationHelper()
}
