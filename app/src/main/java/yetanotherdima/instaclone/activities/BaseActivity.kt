package yetanotherdima.instaclone.activities

import android.content.Intent
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import kotlinx.android.synthetic.main.bottom_nav_view.*
import yetanotherdima.instaclone.*

abstract class BaseActivity(val navNumber: Int?) : AppCompatActivity() {
    private val TAG = "BaseActivity"
    
    protected fun initBottomNavigation() {
        bottom_nav_view.setIconSize(28f, 28f)
        bottom_nav_view.setIconsMarginTop(5)
        bottom_nav_view.setTextVisibility(false)
        bottom_nav_view.enableAnimation(false)
        bottom_nav_view.enableShiftingMode(false)
        bottom_nav_view.enableItemShiftingMode(false)
        if (navNumber != null) {
            bottom_nav_view.menu.getItem(navNumber).isChecked = true
        }
        for (i in 0 until bottom_nav_view.size) {
            bottom_nav_view.setIconTintList(i, null)
        }
        bottom_nav_view.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            val nextActivity =
                when(menuItem.itemId) {
                    R.id.nav_item_home -> HomeActivity::class.java
                    R.id.nav_item_search -> SearchActivity::class.java
                    R.id.nav_item_share -> ShareActivity::class.java
                    R.id.nav_item_favorite -> FavoriteActivity::class.java
                    R.id.nav_item_profile -> ProfileActivity::class.java
                    else -> {
                        Log.d(TAG, "unknown btn clicked: $menuItem")
                        null
                    }
                }

            if (nextActivity != null) {
                val intent = Intent(this, nextActivity)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                startActivity(intent)
                overridePendingTransition(0, 0)
                true
            }

            false
        }
    }
}