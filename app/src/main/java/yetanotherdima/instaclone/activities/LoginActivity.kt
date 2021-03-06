package yetanotherdima.instaclone.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import yetanotherdima.instaclone.R
import yetanotherdima.instaclone.utils.showToast

class LoginActivity : AppCompatActivity(), KeyboardVisibilityEventListener, View.OnClickListener,
    OnCompleteListener<AuthResult> {
    private val TAG = "LoginActivity"
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn.setValidatedTextViews(login_email_field, login_password_field)
        login_btn.setOnClickListener(this)
        sign_up_caption.setOnClickListener(this)

        KeyboardVisibilityEvent.setEventListener(this, this)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onVisibilityChanged(isOpen: Boolean) {
        if (isOpen) {
            sign_up_caption.visibility = View.GONE
        } else {
            sign_up_caption.visibility = View.VISIBLE
        }
    }

    private fun fieldsAreNotEmpty() = login_email_field.text.isNotEmpty() && login_password_field.text.isNotEmpty()

    override fun onClick(v: View) {
        when (v.id) {
            R.id.login_btn -> {
                if (!fieldsAreNotEmpty()) {
                    showToast("Please set both login and password", Toast.LENGTH_SHORT);
                }
                else {
                    mAuth.signInWithEmailAndPassword(login_email_field.text.toString(), login_password_field.text.toString()).addOnCompleteListener(this)
                }
            }
            R.id.sign_up_caption -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    override fun onComplete(authResult: Task<AuthResult>) {
        if (authResult.isSuccessful()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        else {
            showToast( "Incorrect login and/or password!", Toast.LENGTH_SHORT)
        }
    }
}
