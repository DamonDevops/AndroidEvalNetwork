package com.technipixl.exo1

import java.security.MessageDigest

object HashGenerator {
    fun generateHash(timestamp: Long, privateKey: String, publicKey: String): String? {
        val value = "$timestamp$privateKey$publicKey"
        return value.md5()
    }

    private fun String?.md5(): String? {
        if(this.isNullOrEmpty()) return null
        return hashString(this, "MD5")
    }

    private fun hashString(input: String, algorithm: String): String {
        return MessageDigest
            .getInstance(algorithm)
            .digest(input.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
    }
}