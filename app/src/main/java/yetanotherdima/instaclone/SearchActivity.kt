package yetanotherdima.instaclone

import android.os.Bundle
import android.util.Log

class SearchActivity : BaseActivity(1) {
    private val TAG = "SearchActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initBottomNavigation()
        Log.d(TAG, "onCreate: ")
    }
}
