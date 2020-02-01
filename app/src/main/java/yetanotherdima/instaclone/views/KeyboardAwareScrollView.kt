package yetanotherdima.instaclone.views

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView
import kotlinx.android.synthetic.main.activity_login.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

class KeyboardAwareScrollView(context: Context, attrs: AttributeSet) : ScrollView(context, attrs),
    KeyboardVisibilityEventListener {
    init {
        isFillViewport = true
        isVerticalScrollBarEnabled = false
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        KeyboardVisibilityEvent.setEventListener(context as Activity, this)
    }

    override fun onVisibilityChanged(isOpen: Boolean) {
        if (isOpen) {
            scrollTo(0, bottom)
        } else {
            scrollTo(0, top)
        }
    }
}