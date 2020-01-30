package yetanotherdima.instaclone.activities

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

class LoginActivity : AppCompatActivity(), KeyboardVisibilityEventListener, TextWatcher, View.OnClickListener,
    OnCompleteListener<AuthResult> {
    private val TAG = "LoginActivity"
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        KeyboardVisibilityEvent.setEventListener(this, this)
        login_email_field.addTextChangedListener(this)
        login_password_field.addTextChangedListener(this)
        login_btn.isEnabled = false
        login_btn.setOnClickListener(this)

        sign_up_caption.setOnClickListener(this)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onVisibilityChanged(isOpen: Boolean) {
        if (isOpen) {
            login_scroll_view.scrollTo(0, login_scroll_view.bottom);
            sign_up_caption.visibility = View.GONE
        } else {
            login_scroll_view.scrollTo(0, login_scroll_view.top)
            sign_up_caption.visibility = View.VISIBLE
        }
    }

    override fun afterTextChanged(s: Editable?) {
        login_btn.isEnabled = fieldsAreNotEmpty()
    }

    private fun fieldsAreNotEmpty() = login_email_field.text.isNotEmpty() && login_password_field.text.isNotEmpty()

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

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
