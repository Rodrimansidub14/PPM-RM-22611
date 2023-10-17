package fakes

import androidx.fragment.app.Fragment
import com.raywenderlich.rwnews.ui.navigation.NavigationHelper

class FakeNavigationHelper : NavigationHelper {

    data class NavigationInput(
        val anchorId: Int,
        val fragment: Fragment,
        val backStack: String?
    )

    val replaceRequests = mutableListOf<NavigationInput>()

    override fun replace(anchorId: Int, fragment: Fragment, backStack: String?) {
        replaceRequests.add(NavigationInput(anchorId, fragment, backStack))
    }
}
