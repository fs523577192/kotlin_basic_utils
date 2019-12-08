package tech.firas.util

/**
 *
 * @author Wu Yuping
 */
actual typealias Locale = java.util.Locale

actual typealias LocaleCategory = java.util.Locale.Category

actual fun getDefaultLocale(): Locale = Locale.getDefault()
actual fun getDefaultLocale(category: LocaleCategory): Locale = Locale.getDefault(category)