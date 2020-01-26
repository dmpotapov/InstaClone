package yetanotherdima.instaclone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import yetanotherdima.instaclone.R

class HomeActivity : BaseActivity(0), FirebaseAuth.AuthStateListener {
    private val TAG = "HomeActivity"
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initBottomNavigation()
        Log.d(TAG, "onCreate: ")

        mAuth = FirebaseAuth.getInstance()

        test_signout_btn.setOnClickListener() {
            mAuth.signOut()
        }

        mAuth.addAuthStateListener(this)
    }

    override fun onStart() {
        super.onStart()
        checkIfLoggedIn(mAuth)
    }

    override fun onAuthStateChanged(auth: FirebaseAuth) {
        checkIfLoggedIn(auth)
    }

    private fun checkIfLoggedIn(auth: FirebaseAuth) {
        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
