package yetanotherdima.instaclone.views

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_password_dialog.*
import kotlinx.android.synthetic.main.fragment_password_dialog.view.*
import yetanotherdima.instaclone.R

class PasswordDialog : DialogFragment() {
    private lateinit var mListener: Listener

    private lateinit var mView: View

    interface Listener {
        fun onPasswordEnter(password: String)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE).isEnabled = mView.password_fragment_text_view.text.isNotEmpty()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mListener = context as Listener

        mView = activity!!.layoutInflater.inflate(R.layout.fragment_password_dialog, null)
        val dialog = AlertDialog.Builder(context).setView(mView).setPositiveButton(android.R.string.ok, { dialogInterface: DialogInterface, i: Int ->
            mListener.onPasswordEnter(mView.password_fragment_text_view.text.toString())
        }).setNegativeButton(android.R.string.cancel, { dialogInterface: DialogInterface, i: Int -> /* do nothing */ })
            .setTitle(getString(R.string.please_enter_the_password))
        .create()

        dialog.setOnShowListener {
            (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE).isEnabled = false
        }

        mView.password_fragment_text_view.addTextChangedListener(textWatcher)

        return dialog;
    }
}