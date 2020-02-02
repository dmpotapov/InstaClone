package yetanotherdima.instaclone.activities

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import yetanotherdima.instaclone.R
import yetanotherdima.instaclone.models.User
import yetanotherdima.instaclone.utils.ValueEventListenerAdapter
import yetanotherdima.instaclone.utils.onTaskComplete
import yetanotherdima.instaclone.utils.showToast
import yetanotherdima.instaclone.views.PasswordDialog

class EditProfileActivity : AppCompatActivity(), PasswordDialog.Listener {
    private val TAG = "EditProfileActivity"
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    private lateinit var mUser: User
    private lateinit var mPendingUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        Log.d(TAG, "onCreate: ")
        edit_profile_close_btn.setOnClickListener{
            finish()
        }

        edit_profile_save_btn.setOnClickListener{
            updateProfile()
        }

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        val user = mAuth.currentUser

        mDatabase.child("users").child(user!!.uid).addListenerForSingleValueEvent(ValueEventListenerAdapter({ data: DataSnapshot ->
            edit_profile_progress_bar.animate().alpha(0.0f).setDuration(300).setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) { }

                override fun onAnimationEnd(animation: Animator?) {
                    edit_profile_progress_bar.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator?) { }

                override fun onAnimationStart(animation: Animator?) { }
            })


            mUser = data.getValue(User::class.java)!!

            name_field.setText(mUser.name, TextView.BufferType.EDITABLE)
            username_field.setText(mUser.username, TextView.BufferType.EDITABLE)
            website_field.setText(mUser.website, TextView.BufferType.EDITABLE)
            bio_field.setText(mUser.bio, TextView.BufferType.EDITABLE)
            email_field.setText(mUser.email, TextView.BufferType.EDITABLE)
            phone_field.setText(mUser.phone, TextView.BufferType.EDITABLE)
        }))
    }

    private fun updateProfile() {
        val error = validateForm()
        if (error != null) {
            showToast(error)
        }
        else {
            mPendingUser = getPendingUser()
            if (mPendingUser.email == mUser.email) {
                updateUser()
            }
            else {
                PasswordDialog().show(supportFragmentManager, "password_dialog")
            }
        }
    }

    private fun getPendingUser(): User {
        return User(name_field.text.toString(),
                    username_field.text.toString(),
                    website_field.text.toString(),
                    bio_field.text.toString(),
                    email_field.text.toString(),
                    phone_field.text.toString())
    }

    private fun updateUser() {
        Log.d(TAG, "updateUser: ${mPendingUser.toMap()}")
        mDatabase.child("users").child(mAuth.currentUser!!.uid).updateChildren(mPendingUser.toMap()).addOnCompleteListener(
            onTaskComplete({
                showToast("Edited successfully")
                finish()
            }, {
                Log.d(TAG, "updateUser: ${it.exception.toString()}")
                showToast(it.exception.toString())
            })
        )
    }

    private fun validateForm(): String? {
        when {
            name_field.text.isEmpty() -> "Please set name"
            username_field.text.isEmpty() -> "Please set username"
            email_field.text.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email_field.text).matches() -> "Please provide correct email address"
        }

        return null
    }

    override fun onPasswordEnter(password: String) {
        var credentails = EmailAuthProvider.getCredential(mUser.email, password)
        mAuth.currentUser!!.reauthenticate(credentails).addOnCompleteListener(
            onTaskComplete({
                mAuth.currentUser!!.updateEmail(mPendingUser.email).addOnCompleteListener(
                    onTaskComplete({
                        updateUser()
                    }, {
                        Log.d(TAG, "onPasswordEnter: ${it.exception.toString()}")
                        showToast("Couldn't update the email. Try again later")
                    })
                )
            }, {
                Log.d(TAG, "onPasswordEnter: ${it.exception.toString()}")
                showToast("The password is incorrect")
            })
        )
    }
}
