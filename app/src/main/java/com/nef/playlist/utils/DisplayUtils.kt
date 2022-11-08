package com.nef.playlist.utils

import android.content.Context
import android.widget.Toast

fun displayError(context: Context, message: String?) {
    if (!message.isNullOrEmpty()) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    } else {
        Toast.makeText(context, "Unknown error", Toast.LENGTH_LONG).show()
    }
}