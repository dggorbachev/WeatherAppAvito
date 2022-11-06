package com.dggorbachev.weatherapp.base.exts

import java.util.*

object StringExt {

    fun String.firstCharUpper(): String {
        return this.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
        }
    }
}