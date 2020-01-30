package yetanotherdima.instaclone.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_register_email.*
import kotlinx.android.synthetic.main.fragment_register_user_data.*
import yetanotherdima.instaclone.R
import yetanotherdima.instaclone.models.User
import yetanotherdima.instaclone.utils.showToast
import java.util.zip.Inflater

class RegisterActivity : AppCompatActivity(), EmailFragment.Listener, UserDataFragment.Listener {
    private lateinit var mEmail: String
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDb: DatabaseReference

    private val TAG = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.register_frame_layout, EmailFragment()).commit()
        }
        mAuth = FirebaseAuth.getInstance()
        mDb = FirebaseDatabase.getInstance().reference
    }

    override fun onContinue(email: String) {
        if (email.isEmpty()) {
            showToast("Email is empty")
        }
        else {
            mEmail = email!!
            supportFragmentManager.beginTransaction().replace(R.id.register_frame_layout, UserDataFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onStepBack() {
        supportFragmentManager.popBackStack()
    }

    override fun onRegister(fullname: String, username: String, password: String) {
        if (fullname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showToast("Please fill all fields")
        }
        else {
            mAuth.createUserWithEmailAndPassword(mEmail, password).addOnCompleteListener {
                it.onTaskComplete({
                    val user = User(fullname, username, "", "", mEmail, "")
                    val ref = mDb.child("users").child((it as Task<AuthResult>).result!!.user!!.uid)
                    ref.setValue(user).addOnCompleteListener {
                        it.onTaskComplete({
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        }, {
                            showToast("Unable to create the profile")
                            Log.d(TAG, "onRegister: ", it.exception)
                        })
                    }
                }, {
                    showToast("Unable to create the user")
                    Log.d(TAG, "onRegister: ", it.exception)
                })
            }
        }
    }
}

fun Task<*>.onTaskComplete(resolve: (Task<*>) -> Unit, reject: (Task<*>) -> Unit) {
    if (this.isSuccessful()) {
        resolve(this)
    }
    else {
        reject(this)
    }
}

class EmailFragment : Fragment() {
    private lateinit var mListener: Listener

    interface Listener {
        fun onContinue(email: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        continue_btn.setOnClickListener {
            mListener.onContinue(register_email_input.text.toString())
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mListener = context as Listener
    }
}

class UserDataFragment : Fragment() {
    private lateinit var mListener: UserDataFragment.Listener

    interface Listener {
        fun onStepBack()
        fun onRegister(fullname: String, username: String, password: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_user_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register_btn.setOnClickListener {
            mListener.onRegister(register_fullname_input.text.toString(),
                                 register_username_input.text.toString(),
                                 register_password_input.text.toString())
        }

        register_step_back.setOnClickListener {
            mListener.onStepBack()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mListener = context as Listener
    }
}