package yetanotherdima.instaclone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_profile.*
import yetanotherdima.instaclone.R

class ProfileActivity : BaseActivity(4) {
    private val TAG = "ProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initBottomNavigation()
        Log.d(TAG, "onCreate: ")

        edit_profile_btn.setOnClickListener{
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }
    }
}
