package org.firas.util

/**
 *
 * @author Wu Yuping
 */
expect class Locale {

    constructor(language: String?, country: String?, variant: String?)
    constructor(language: String, country: String)
    constructor(language: String)

    fun getLanguage(): String
    fun getScript(): String
    fun getCountry(): String
    fun getVariant(): String

    fun toLanguageTag(): String
}