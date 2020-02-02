package yetanotherdima.instaclone.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
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

fun Context.showToast(text: String, length: Int = Toast.LENGTH_SHORT): Unit = Toast.makeText(this, text, length).show();

fun onTaskComplete(resolve: (Task<*>) -> Unit, reject: ((Task<*>) -> Unit)?) : ((Task<*>) -> Unit) {
    return {
        if (it.isSuccessful()) {
            resolve(it)
        }
        else if (reject != null) {
            reject(it)
        }
    }
}