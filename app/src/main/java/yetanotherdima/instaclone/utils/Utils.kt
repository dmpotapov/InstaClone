package yetanotherdima.instaclone.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ValueEventListenerAdapter(private val handler: (DataSnapshot) -> Unit) : ValueEventListener {
    private val TAG = "ValEventListenerAdapter"

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        handler(dataSnapshot)
    }

    override fun onCancelled(error: DatabaseError) {
        Log.d(TAG, "onCancelled: something happened while trying to fetch the user info:", error.toException())
    }
}

fun Context.showToast(text: String, length: Int): Unit = Toast.makeText(this, text, length).show();