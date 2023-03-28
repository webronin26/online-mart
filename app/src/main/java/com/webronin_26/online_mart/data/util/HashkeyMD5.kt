package com.webronin_26.online_mart.data.util

import java.security.MessageDigest

class HashKeyMD5 {

    fun hashKeyFromString(targetString: String): String {

        val hashString: String
        val sb = StringBuilder()

        hashString =
            try {
                val messageDigest: MessageDigest = MessageDigest.getInstance("MD5")
                messageDigest.update(targetString.toByteArray())
                val bytes: ByteArray = messageDigest.digest()

                for (i in bytes.indices) {
                    val hex = Integer.toHexString(0xFF and bytes[i].toInt())
                    if (hex.length == 1) { sb.append('0') }
                    sb.append(hex)
                }

            sb.toString()
        } catch (e: Exception) {
            targetString.hashCode().toString()
        }
        return hashString
    }
}