package com.raywenderlich.rwnews.ui.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

/** Abstraction for the object that provides navigation utility */
interface NavigationHelper {

  fun replace(@IdRes anchorId: Int, fragment: Fragment, backStack: String? = null)
}