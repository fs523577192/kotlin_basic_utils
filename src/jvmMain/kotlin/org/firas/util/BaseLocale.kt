package org.firas.util

import java.lang.ref.SoftReference

/**
 *
 * @author Wu Yuping
 */
internal actual class BaseLocaleKey {

    /**
     * Keep a SoftReference to the Key data if normalized (actually used
     * as a cache key) and not initialized via the constant creation path.
     *
     * This allows us to avoid creating SoftReferences on lookup Keys
     * (which are short-lived) and for Locales created via
     * Locale#createConstant.
     */
    internal val holderRef: SoftReference<BaseLocale>?
    internal val holder: BaseLocale?

    internal val normalized: Boolean
    private val hash: Int

    /**
     * Creates a Key. language and region must be normalized
     * (intern'ed in the proper case).
     */
    internal actual constructor(locale: BaseLocale) {
        this.holder = locale
        this.holderRef = null
        this.normalized = true
        val language = locale.language
        val region = locale.region
        assert(
            LocaleUtils.toLowerString(language).intern() === language
            && LocaleUtils.toUpperString(region).intern() === region
            && locale.variant == ""
            && locale.script == ""
        )

        var h = language.hashCode()
        if (region != "") {
            val len = region.length
            for (i in 0 until len) {
                h = 31 * h + LocaleUtils.toLower(region[i]).toInt()
            }
        }
        this.hash = h
    }

    internal actual constructor(
        language: String?,
        script: String?,
        region: String?,
        variant: String?,
        normalize: Boolean
    ) {
        val _language = language ?: ""
        val _script = script ?: ""
        val _region = region ?: ""
        val _variant = variant ?: ""

        val locale = BaseLocale(_language, _script, _region, _variant, normalize)
        this.normalized = normalize
        if (normalized) {
            this.holderRef = SoftReference(locale)
            this.holder = null
        } else {
            this.holderRef = null
            this.holder = locale
        }
        this.hash = hashCode(locale)
    }

    private fun hashCode(locale: BaseLocale): Int {
        var h = 0
        val lang = locale.language
        var len = lang.length
        for (i in 0 until len) {
            h = 31 * h + LocaleUtils.toLower(lang[i]).toInt()
        }
        val scrt = locale.script
        len = scrt.length
        for (i in 0 until len) {
            h = 31 * h + LocaleUtils.toLower(scrt[i]).toInt()
        }
        val regn = locale.region
        len = regn.length
        for (i in 0 until len) {
            h = 31 * h + LocaleUtils.toLower(regn[i]).toInt()
        }
        val vart = locale.variant
        len = vart.length
        for (i in 0 until len) {
            h = 31 * h + vart[i].toInt()
        }
        return h
    }

    internal actual fun getBaseLocale(): BaseLocale? {
        return this.holder ?: this.holderRef!!.get()
    }

    override fun hashCode(): Int {
        return this.hash
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other is BaseLocaleKey && this.hash == other.hash) {
            val _otherBaseLocale = other.getBaseLocale()!!
            val locale = this.getBaseLocale()
            if (locale != null
                && LocaleUtils.caseIgnoreMatch(_otherBaseLocale.language, locale.language)
                && LocaleUtils.caseIgnoreMatch(_otherBaseLocale.script, locale.script)
                && LocaleUtils.caseIgnoreMatch(_otherBaseLocale.region, locale.region)
                // variant is case sensitive in JDK!
                && _otherBaseLocale.variant == locale.variant
            ) {
                return true
            }
        }
        return false
    }
}

internal actual fun normalizeBaseLocaleKey(key: BaseLocaleKey): BaseLocaleKey {
    if (key.normalized) {
        return key
    }

    // Only normalized keys may be softly referencing the data holder
    assert(key.holder != null && key.holderRef == null)
    val locale = key.holder!!
    return BaseLocaleKey(
        locale.language, locale.script,
        locale.region, locale.variant, true
    )
}