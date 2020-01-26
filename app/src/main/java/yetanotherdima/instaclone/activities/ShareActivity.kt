package yetanotherdima.instaclone.activities

import android.os.Bundle
import android.util.Log
import yetanotherdima.instaclone.R

class ShareActivity : BaseActivity(2) {
    private val TAG = "ShareActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        initBottomNavigation()
        Log.d(TAG, "onCreate: ")
    }
}
