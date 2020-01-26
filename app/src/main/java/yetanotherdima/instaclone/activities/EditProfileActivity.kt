package yetanotherdima.instaclone.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import yetanotherdima.instaclone.R
import yetanotherdima.instaclone.models.User
import yetanotherdima.instaclone.utils.ValueEventListenerAdapter

class EditProfileActivity : AppCompatActivity() {
    private val TAG = "EditProfileActivity"
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        Log.d(TAG, "onCreate: ")

        edit_profile_close_btn.setOnClickListener{
            finish()
        }

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        val user = mAuth.currentUser

        mDatabase.child("users").child(user!!.uid).addListenerForSingleValueEvent(ValueEventListenerAdapter({ data: DataSnapshot ->
            val u = data.getValue(User::class.java)

            name_field.setText(u!!.name, TextView.BufferType.EDITABLE)
            username_field.setText(u!!.username, TextView.BufferType.EDITABLE)
            website_field.setText(u!!.website, TextView.BufferType.EDITABLE)
            bio_field.setText(u!!.bio, TextView.BufferType.EDITABLE)
            email_field.setText(u!!.email, TextView.BufferType.EDITABLE)
            phone_field.setText(u!!.phone, TextView.BufferType.EDITABLE)
        }))
    }
}
