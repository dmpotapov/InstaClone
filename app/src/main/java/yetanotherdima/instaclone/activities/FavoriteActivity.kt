package yetanotherdima.instaclone.activities

import android.os.Bundle
import android.util.Log
import yetanotherdima.instaclone.R

class FavoriteActivity : BaseActivity(3) {
    private val TAG = "FavoriteActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        initBottomNavigation()
        Log.d(TAG, "onCreate: ")
    }
}
