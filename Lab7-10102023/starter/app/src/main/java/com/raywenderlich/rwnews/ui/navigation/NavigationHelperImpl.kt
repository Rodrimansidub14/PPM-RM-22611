package com.raywenderlich.rwnews.ui.navigation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

/** NavigationHelper implementation */
@ActivityScoped
class NavigationHelperImpl @Inject constructor(
    @ActivityContext private val owner: Context
) : NavigationHelper {

  private val ownerActivity = owner as AppCompatActivity

  override fun replace(anchorId: Int, fragment: Fragment, backStack: String?) {
    ownerActivity.supportFragmentManager.beginTransaction()
        .replace(anchorId, fragment)
        .apply {
          backStack?.let {
            addToBackStack(it)
          }
        }
        .commit()
  }
}