package com.raywenderlich.rwnews

import android.provider.SyncStateContract.Helpers.insert
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.apps.common.testing.accessibility.framework.replacements.Uri
import com.raywenderlich.rwnews.di.AppModule
import com.raywenderlich.rwnews.logger.RwNewsLogger
import com.raywenderlich.rwnews.repository.NewsRepository
import com.raywenderlich.rwnews.repository.entity.News
import com.raywenderlich.rwnews.ui.list.NewsListFragment
import com.raywenderlich.rwnews.ui.list.NewsListItemViewHolder
import com.raywenderlich.rwnews.util.launchFragmentInHiltContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class) // HERE
class NewsListFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    // Moved TestAppModule out of the nested class
    @Module
    @InstallIn(SingletonComponent::class) // 1
    object TestAppModule {

        @Provides
        fun provideNewsRepository(): NewsRepository { // 2
            return FakeNewsRepository().apply {
                insert(News(1, "First Title", "First Body"))
                insert(News(2, "Second Title", "Second Body"))
                insert(News(3, "Third Title", "Third Body"))
            }
        }

        @Provides
        fun provideNewsLogger(): RwNewsLogger = FakeNewsLogger() // 2
    }

    @Test
    fun whenDisplayed_newsListFromRepoIsDisplayed() { // 1
        launchFragmentInHiltContainer<NewsListFragment>() // 2
        scrollAtAndCheckTestVisible(0, "First Title")
        scrollAtAndCheckTestVisible(1, "Second Title")
        scrollAtAndCheckTestVisible(2, "Third Title")
    }

    fun scrollAtAndCheckTestVisible(position: Int, text: String) {
        onView(ViewMatchers.withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions
                    .scrollToPosition<NewsListItemViewHolder>(position)
            )
        onView(withText(text)).check(matches(isDisplayed()))
    }
}

class FakeNewsRepository : NewsRepository {

    var data = mutableMapOf<Long, News>()
    var listInvokeCount = 0

    override fun byId(id: Long): News? = data[id]

    override fun list(): List<News> {
        listInvokeCount++
        return data.values.toList()
    }

    override fun insert(news: News) {
        data[news.id] = news
    }
}
class FakeNewsLogger : RwNewsLogger {

    val logInfoMessages = mutableListOf<String>()
    val logErrorMessages = mutableListOf<Pair<String, Throwable?>>()

    override fun logInfo(msg: String) {
        logInfoMessages.add(msg)
    }

    override fun logError(msg: String, throwable: Throwable?) {
        logErrorMessages.add(msg to throwable)
    }
}
