package tech.firas.util

/**
 *
 * @author Wu Yuping (migrate from Java 11)
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

expect enum class LocaleCategory {
    DISPLAY, FORMAT
}

expect fun getDefaultLocale(): Locale
expect fun getDefaultLocale(category: LocaleCategory): Locale