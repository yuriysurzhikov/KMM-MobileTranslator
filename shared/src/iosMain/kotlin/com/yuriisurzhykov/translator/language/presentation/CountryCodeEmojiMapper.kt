package com.yuriisurzhykov.translator.language.presentation

import com.yuriisurzhykov.translator.core.Mapper

actual class CountryCodeEmojiMapper : Mapper<String, String> {

    override fun map(input: String): String {
        val code = input.uppercase()
        val base = 127397
        val usv = StringBuilder()
        for (i in code.toCharArray()) {
            usv.append(base + i.code)
        }
        return usv.toString()
    }
}