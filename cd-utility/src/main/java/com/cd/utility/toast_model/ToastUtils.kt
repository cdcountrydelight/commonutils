package com.cd.utility.toast_model

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
* how to  use it
* */


/**
* "This is my string".showToast(this)
* */

fun Any.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(context, this.toString(), duration).apply { show() }
}

/**
* showToast("Demo")
* showToast("Demo", Toast.LENGTH_LONG)
* */
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message , duration).show()
}


/**
* context.showToast(R.string.toast_message)
* */

fun Context.showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, this.resources.getText(resId), duration).show()
}

/**
 * context.showToast(R.string.toast_message,"Message")
 * */

fun Context.showToast(@StringRes resId: Int, msg: String,duration: Int = Toast.LENGTH_SHORT){
    showToast(getString(resId) + msg)
}

/**
 * context.showToast("Message",R.string.toast_message)
 * */

fun Context.showToast(msg: String,@StringRes resId: Int,duration: Int = Toast.LENGTH_SHORT){
    showToast(msg + getString(resId))
}