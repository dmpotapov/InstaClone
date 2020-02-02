package yetanotherdima.instaclone.views

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*
import yetanotherdima.instaclone.R

class BasicValidateAwareButton(context: Context, attrs: AttributeSet) : Button(context, attrs) {
    private val TAG = "BasicValidateAwareButto"
    private val mValidatedTextViews: MutableList<TextView> = mutableListOf()
    private val textWatcher: BasicValidateAwareButtonTextWatcher = BasicValidateAwareButtonTextWatcher(this)

    init {
        isEnabled = false
    }

    class BasicValidateAwareButtonTextWatcher(private val btn: BasicValidateAwareButton) : TextWatcher {
        private val TAG = "BasicValidateAwareButto"

        override fun afterTextChanged(s: Editable?) {
            Log.d(TAG, "afterTextChanged: ")
            btn.isEnabled = btn.fieldsAreNotEmpty()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    }

    fun setValidatedTextViews(vararg textViews: TextView) {
        Log.d(TAG, "setValidatedTextViews: ${textViews.size}")

        for (textView in textViews) {
            mValidatedTextViews.add(textView)
            textView.addTextChangedListener(textWatcher)
        }
    }

    fun fieldsAreNotEmpty(): Boolean {
        Log.d(TAG, "fieldsAreNotEmpty: ")
        for (textView in mValidatedTextViews) {
            if (textView.text.isEmpty()) {
                return false
            }
        }

        return true
    }


}