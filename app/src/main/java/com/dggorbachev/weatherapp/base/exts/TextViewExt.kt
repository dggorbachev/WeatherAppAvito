package com.dggorbachev.weatherapp.base.exts

import android.widget.TextView
import androidx.annotation.StringRes

object TextViewExt {

    fun TextView.setMessage(@StringRes attrString: Int, message: String) {
        this.text = context.getString(attrString, message)
    }

    fun TextView.setMessage2Params(
        @StringRes attrString: Int,
        message1: String,
        message2: String,
    ) {
        this.text = context.getString(attrString, message1,message2)
    }
}