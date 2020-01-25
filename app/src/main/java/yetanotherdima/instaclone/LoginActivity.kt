package yetanotherdima.instaclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

class LoginActivity : AppCompatActivity(), KeyboardVisibilityEventListener, TextWatcher {
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        KeyboardVisibilityEvent.setEventListener(this, this)
        login_email_field.addTextChangedListener(this)
        login_password_field.addTextChangedListener(this)
        login_btn.isEnabled = false
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
        login_btn.isEnabled = login_email_field.text.isNotEmpty() && login_password_field.text.isNotEmpty()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
}
