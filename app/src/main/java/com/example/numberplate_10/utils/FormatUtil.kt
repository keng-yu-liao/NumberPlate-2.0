package com.example.numberplate_10.utils

class FormatUtil {
    companion object {

        fun unicodeToString(unicodeStr: String): String {
            val str = unicodeStr.replace("0x", "\\")
            val hex = str.split("\\u")
            val turn = hex.size - 1
            val string = StringBuffer()

            for (i in 1..turn ) {
                val data = Integer.parseInt(hex[i], 16).toChar()
                string.append(data)

            }

            return string.toString()
        }

    }
}