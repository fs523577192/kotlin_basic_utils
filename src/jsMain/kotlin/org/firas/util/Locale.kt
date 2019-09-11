package org.firas.util

/**
 * A <code>Locale</code> object represents a specific geographical, political,
 * or cultural region. An operation that requires a <code>Locale</code> to perform
 * its task is called <em>locale-sensitive</em> and uses the <code>Locale</code>
 * to tailor information for the user. For example, displaying a number
 * is a locale-sensitive operation&mdash; the number should be formatted
 * according to the customs and conventions of the user's native country,
 * region, or culture.
 *
 * <p> The `Locale` class implements IETF BCP 47 which is composed of
 * <a href="http://tools.ietf.org/html/rfc4647">RFC 4647 "Matching of Language
 * Tags"</a> and <a href="http://tools.ietf.org/html/rfc5646">RFC 5646 "Tags
 * for Identifying Languages"</a> with support for the LDML (UTS#35, "Unicode
 * Locale Data Markup Language") BCP 47-compatible extensions for locale data
 * exchange.
 *
 * <p> A <code>Locale</code> object logically consists of the fields
 * described below.
 *
 * <dl>
 *   <dt><a id="def_language"><b>language</b></a></dt>
 *
 *   <dd>ISO 639 alpha-2 or alpha-3 language code, or registered
 *   language subtags up to 8 alpha letters (for future enhancements).
 *   When a language has both an alpha-2 code and an alpha-3 code, the
 *   alpha-2 code must be used.  You can find a full list of valid
 *   language codes in the IANA Language Subtag Registry (search for
 *   "Type: language").  The language field is case insensitive, but
 *   <code>Locale</code> always canonicalizes to lower case.</dd>
 *
 *   <dd>Well-formed language values have the form
 *   <code>[a-zA-Z]{2,8}</code>.  Note that this is not the full
 *   BCP47 language production, since it excludes extlang.  They are
 *   not needed since modern three-letter language codes replace
 *   them.</dd>
 *
 *   <dd>Example: "en" (English), "ja" (Japanese), "kok" (Konkani)</dd>
 *
 *   <dt><a id="def_script"><b>script</b></a></dt>
 *
 *   <dd>ISO 15924 alpha-4 script code.  You can find a full list of
 *   valid script codes in the IANA Language Subtag Registry (search
 *   for "Type: script").  The script field is case insensitive, but
 *   <code>Locale</code> always canonicalizes to title case (the first
 *   letter is upper case and the rest of the letters are lower
 *   case).</dd>
 *
 *   <dd>Well-formed script values have the form
 *   <code>[a-zA-Z]{4}</code></dd>
 *
 *   <dd>Example: "Latn" (Latin), "Cyrl" (Cyrillic)</dd>
 *
 *   <dt><a id="def_region"><b>country (region)</b></a></dt>
 *
 *   <dd>ISO 3166 alpha-2 country code or UN M.49 numeric-3 area code.
 *   You can find a full list of valid country and region codes in the
 *   IANA Language Subtag Registry (search for "Type: region").  The
 *   country (region) field is case insensitive, but
 *   <code>Locale</code> always canonicalizes to upper case.</dd>
 *
 *   <dd>Well-formed country/region values have
 *   the form <code>[a-zA-Z]{2} | [0-9]{3}</code></dd>
 *
 *   <dd>Example: "US" (United States), "FR" (France), "029"
 *   (Caribbean)</dd>
 *
 *   <dt><a id="def_variant"><b>variant</b></a></dt>
 *
 *   <dd>Any arbitrary value used to indicate a variation of a
 *   <code>Locale</code>.  Where there are two or more variant values
 *   each indicating its own semantics, these values should be ordered
 *   by importance, with most important first, separated by
 *   underscore('_').  The variant field is case sensitive.</dd>
 *
 *   <dd>Note: IETF BCP 47 places syntactic restrictions on variant
 *   subtags.  Also BCP 47 subtags are strictly used to indicate
 *   additional variations that define a language or its dialects that
 *   are not covered by any combinations of language, script and
 *   region subtags.  You can find a full list of valid variant codes
 *   in the IANA Language Subtag Registry (search for "Type: variant").
 *
 *   <p>However, the variant field in <code>Locale</code> has
 *   historically been used for any kind of variation, not just
 *   language variations.  For example, some supported variants
 *   available in Java SE Runtime Environments indicate alternative
 *   cultural behaviors such as calendar type or number script.  In
 *   BCP 47 this kind of information, which does not identify the
 *   language, is supported by extension subtags or private use
 *   subtags.</dd>
 *
 *   <dd>Well-formed variant values have the form <code>SUBTAG
 *   (('_'|'-') SUBTAG)*</code> where <code>SUBTAG =
 *   [0-9][0-9a-zA-Z]{3} | [0-9a-zA-Z]{5,8}</code>. (Note: BCP 47 only
 *   uses hyphen ('-') as a delimiter, this is more lenient).</dd>
 *
 *   <dd>Example: "polyton" (Polytonic Greek), "POSIX"</dd>
 *
 *   <dt><a id="def_extensions"><b>extensions</b></a></dt>
 *
 *   <dd>A map from single character keys to string values, indicating
 *   extensions apart from language identification.  The extensions in
 *   <code>Locale</code> implement the semantics and syntax of BCP 47
 *   extension subtags and private use subtags. The extensions are
 *   case insensitive, but <code>Locale</code> canonicalizes all
 *   extension keys and values to lower case. Note that extensions
 *   cannot have empty values.</dd>
 *
 *   <dd>Well-formed keys are single characters from the set
 *   <code>[0-9a-zA-Z]</code>.  Well-formed values have the form
 *   <code>SUBTAG ('-' SUBTAG)*</code> where for the key 'x'
 *   <code>SUBTAG = [0-9a-zA-Z]{1,8}</code> and for other keys
 *   <code>SUBTAG = [0-9a-zA-Z]{2,8}</code> (that is, 'x' allows
 *   single-character subtags).</dd>
 *
 *   <dd>Example: key="u"/value="ca-japanese" (Japanese Calendar),
 *   key="x"/value="java-1-7"</dd>
 * </dl>
 *
 * <b>Note:</b> Although BCP 47 requires field values to be registered
 * in the IANA Language Subtag Registry, the <code>Locale</code> class
 * does not provide any validation features.  The <code>Builder</code>
 * only checks if an individual field satisfies the syntactic
 * requirement (is well-formed), but does not validate the value
 * itself.  See {@link Builder} for details.
 *
 * <h3><a id="def_locale_extension">Unicode locale/language extension</a></h3>
 *
 * <p>UTS#35, "Unicode Locale Data Markup Language" defines optional
 * attributes and keywords to override or refine the default behavior
 * associated with a locale.  A keyword is represented by a pair of
 * key and type.  For example, "nu-thai" indicates that Thai local
 * digits (value:"thai") should be used for formatting numbers
 * (key:"nu").
 *
 * <p>The keywords are mapped to a BCP 47 extension value using the
 * extension key 'u' ({@link #UNICODE_LOCALE_EXTENSION}).  The above
 * example, "nu-thai", becomes the extension "u-nu-thai".
 *
 * <p>Thus, when a <code>Locale</code> object contains Unicode locale
 * attributes and keywords,
 * <code>getExtension(UNICODE_LOCALE_EXTENSION)</code> will return a
 * String representing this information, for example, "nu-thai".  The
 * <code>Locale</code> class also provides {@link
 * #getUnicodeLocaleAttributes}, {@link #getUnicodeLocaleKeys}, and
 * {@link #getUnicodeLocaleType} which allow you to access Unicode
 * locale attributes and key/type pairs directly.  When represented as
 * a string, the Unicode Locale Extension lists attributes
 * alphabetically, followed by key/type sequences with keys listed
 * alphabetically (the order of subtags comprising a key's type is
 * fixed when the type is defined)
 *
 * <p>A well-formed locale key has the form
 * <code>[0-9a-zA-Z]{2}</code>.  A well-formed locale type has the
 * form <code>"" | [0-9a-zA-Z]{3,8} ('-' [0-9a-zA-Z]{3,8})*</code> (it
 * can be empty, or a series of subtags 3-8 alphanums in length).  A
 * well-formed locale attribute has the form
 * <code>[0-9a-zA-Z]{3,8}</code> (it is a single subtag with the same
 * form as a locale type subtag).
 *
 * <p>The Unicode locale extension specifies optional behavior in
 * locale-sensitive services.  Although the LDML specification defines
 * various keys and values, actual locale-sensitive service
 * implementations in a Java Runtime Environment might not support any
 * particular Unicode locale attributes or key/type pairs.
 *
 * <h4>Creating a Locale</h4>
 *
 * <p>There are several different ways to create a <code>Locale</code>
 * object.
 *
 * <h5>Builder</h5>
 *
 * <p>Using {@link Builder} you can construct a <code>Locale</code> object
 * that conforms to BCP 47 syntax.
 *
 * <h5>Constructors</h5>
 *
 * <p>The <code>Locale</code> class provides three constructors:
 * <blockquote>
 * <pre>
 *     {@link #Locale(String language)}
 *     {@link #Locale(String language, String country)}
 *     {@link #Locale(String language, String country, String variant)}
 * </pre>
 * </blockquote>
 * These constructors allow you to create a <code>Locale</code> object
 * with language, country and variant, but you cannot specify
 * script or extensions.
 *
 * <h5>Factory Methods</h5>
 *
 * <p>The method {@link #forLanguageTag} creates a <code>Locale</code>
 * object for a well-formed BCP 47 language tag.
 *
 * <h5>Locale Constants</h5>
 *
 * <p>The <code>Locale</code> class provides a number of convenient constants
 * that you can use to create <code>Locale</code> objects for commonly used
 * locales. For example, the following creates a <code>Locale</code> object
 * for the United States:
 *
 * ```
 *     Locale.US
 * ```
 *
 * <h4><a id="LocaleMatching">Locale Matching</a></h4>
 *
 * <p>If an application or a system is internationalized and provides localized
 * resources for multiple locales, it sometimes needs to find one or more
 * locales (or language tags) which meet each user's specific preferences. Note
 * that a term "language tag" is used interchangeably with "locale" in this
 * locale matching documentation.
 *
 * <p>In order to do matching a user's preferred locales to a set of language
 * tags, <a href="http://tools.ietf.org/html/rfc4647">RFC 4647 Matching of
 * Language Tags</a> defines two mechanisms: filtering and lookup.
 * <em>Filtering</em> is used to get all matching locales, whereas
 * <em>lookup</em> is to choose the best matching locale.
 * Matching is done case-insensitively. These matching mechanisms are described
 * in the following sections.
 *
 * <p>A user's preference is called a <em>Language Priority List</em> and is
 * expressed as a list of language ranges. There are syntactically two types of
 * language ranges: basic and extended. See
 * {@link Locale.LanguageRange Locale.LanguageRange} for details.
 *
 * <h5>Filtering</h5>
 *
 * <p>The filtering operation returns all matching language tags. It is defined
 * in RFC 4647 as follows:
 * "In filtering, each language range represents the least specific language
 * tag (that is, the language tag with fewest number of subtags) that is an
 * acceptable match. All of the language tags in the matching set of tags will
 * have an equal or greater number of subtags than the language range. Every
 * non-wildcard subtag in the language range will appear in every one of the
 * matching language tags."
 *
 * <p>There are two types of filtering: filtering for basic language ranges
 * (called "basic filtering") and filtering for extended language ranges
 * (called "extended filtering"). They may return different results by what
 * kind of language ranges are included in the given Language Priority List.
 * {@link Locale.FilteringMode} is a parameter to specify how filtering should
 * be done.
 *
 * <h5>Lookup</h5>
 *
 * <p>The lookup operation returns the best matching language tags. It is
 * defined in RFC 4647 as follows:
 * "By contrast with filtering, each language range represents the most
 * specific tag that is an acceptable match.  The first matching tag found,
 * according to the user's priority, is considered the closest match and is the
 * item returned."
 *
 * <p>For example, if a Language Priority List consists of two language ranges,
 * `"zh-Hant-TW"` and `"en-US"`, in prioritized order, lookup
 * method progressively searches the language tags below in order to find the
 * best matching language tag.
 * <blockquote>
 * <pre>
 *    1. zh-Hant-TW
 *    2. zh-Hant
 *    3. zh
 *    4. en-US
 *    5. en
 * </pre>
 * </blockquote>
 * If there is a language tag which matches completely to a language range
 * above, the language tag is returned.
 *
 * <p>`"*"` is the special language range, and it is ignored in lookup.
 *
 * <p>If multiple language tags match as a result of the subtag `'*'`
 * included in a language range, the first matching language tag returned by
 * an {@link Iterator} over a {@link Collection} of language tags is treated as
 * the best matching one.
 *
 * <h4>Use of Locale</h4>
 *
 * <p>Once you've created a <code>Locale</code> you can query it for information
 * about itself. Use <code>getCountry</code> to get the country (or region)
 * code and <code>getLanguage</code> to get the language code.
 * You can use <code>getDisplayCountry</code> to get the
 * name of the country suitable for displaying to the user. Similarly,
 * you can use <code>getDisplayLanguage</code> to get the name of
 * the language suitable for displaying to the user. Interestingly,
 * the <code>getDisplayXXX</code> methods are themselves locale-sensitive
 * and have two versions: one that uses the default
 * {@link Locale.Category#DISPLAY DISPLAY} locale and one
 * that uses the locale specified as an argument.
 *
 * <p>The Java Platform provides a number of classes that perform locale-sensitive
 * operations. For example, the <code>NumberFormat</code> class formats
 * numbers, currency, and percentages in a locale-sensitive manner. Classes
 * such as <code>NumberFormat</code> have several convenience methods
 * for creating a default object of that type. For example, the
 * <code>NumberFormat</code> class provides these three convenience methods
 * for creating a default <code>NumberFormat</code> object:
 *
 * ```
 *     NumberFormat.getInstance()
 *     NumberFormat.getCurrencyInstance()
 *     NumberFormat.getPercentInstance()
 * ```
 *
 * Each of these methods has two variants; one with an explicit locale
 * and one without; the latter uses the default
 * {@link Locale.Category#FORMAT FORMAT} locale:
 *
 * ```
 *     NumberFormat.getInstance(myLocale)
 *     NumberFormat.getCurrencyInstance(myLocale)
 *     NumberFormat.getPercentInstance(myLocale)
 * ```
 *
 * A <code>Locale</code> is the mechanism for identifying the kind of object
 * (<code>NumberFormat</code>) that you would like to get. The locale is
 * <STRONG>just</STRONG> a mechanism for identifying objects,
 * <STRONG>not</STRONG> a container for the objects themselves.
 *
 * <h4>Compatibility</h4>
 *
 *
 * In order to maintain compatibility with existing usage, Locale's
 * constructors retain their behavior prior to the Java Runtime
 * Environment version 1.7.  The same is largely true for the
 * `toString` method. Thus Locale objects can continue to
 * be used as they were. In particular, clients who parse the output
 * of toString into language, country, and variant fields can continue
 * to do so (although this is strongly discouraged), although the
 * variant field will have additional information in it if script or
 * extensions are present.
 *
 *
 * In addition, BCP 47 imposes syntax restrictions that are not
 * imposed by Locale's constructors. This means that conversions
 * between some Locales and BCP 47 language tags cannot be made without
 * losing information. Thus `toLanguageTag` cannot
 * represent the state of locales whose language, country, or variant
 * do not conform to BCP 47.
 *
 *
 * Because of these issues, it is recommended that clients migrate
 * away from constructing non-conforming locales and use the
 * `forLanguageTag` and `Locale.Builder` APIs instead.
 * Clients desiring a string representation of the complete locale can
 * then always rely on `toLanguageTag` for this purpose.
 *
 * <h5><a id="special_cases_constructor">Special cases</a></h5>
 *
 *
 * For compatibility reasons, two
 * non-conforming locales are treated as special cases.  These are
 * <b>`ja_JP_JP`</b> and <b>`th_TH_TH`</b>. These are ill-formed
 * in BCP 47 since the variants are too short. To ease migration to BCP 47,
 * these are treated specially during construction.  These two cases (and only
 * these) cause a constructor to generate an extension, all other values behave
 * exactly as they did prior to Java 7.
 *
 *
 * Java has used `ja_JP_JP` to represent Japanese as used in
 * Japan together with the Japanese Imperial calendar. This is now
 * representable using a Unicode locale extension, by specifying the
 * Unicode locale key `ca` (for "calendar") and type
 * `japanese`. When the Locale constructor is called with the
 * arguments "ja", "JP", "JP", the extension "u-ca-japanese" is
 * automatically added.
 *
 *
 * Java has used `th_TH_TH` to represent Thai as used in
 * Thailand together with Thai digits. This is also now representable using
 * a Unicode locale extension, by specifying the Unicode locale key
 * `nu` (for "number") and value `thai`. When the Locale
 * constructor is called with the arguments "th", "TH", "TH", the
 * extension "u-nu-thai" is automatically added.
 *
 * <h5>Serialization</h5>
 *
 *
 * During serialization, writeObject writes all fields to the output
 * stream, including extensions.
 *
 *
 * During deserialization, readResolve adds extensions as described
 * in <a href="#special_cases_constructor">Special Cases</a>, only
 * for the two cases th_TH_TH and ja_JP_JP.
 *
 * <h5>Legacy language codes</h5>
 *
 *
 * Locale's constructor has always converted three language codes to
 * their earlier, obsoleted forms: `he` maps to `iw`,
 * `yi` maps to `ji`, and `id` maps to
 * `in`.  This continues to be the case, in order to not break
 * backwards compatibility.
 *
 *
 * The APIs added in 1.7 map between the old and new language codes,
 * maintaining the old codes internal to Locale (so that
 * `getLanguage` and `toString` reflect the old code),
 * but using the new codes in the BCP 47 language tag APIs
 * (so that `toLanguageTag` reflects the new one). This
 * preserves the equivalence between Locales no matter which code or
 * API is used to construct them. Java's default resource bundle
 * lookup mechanism also implements this mapping, so that resources
 * can be named using either convention, see {@link ResourceBundle.Control}.
 *
 * <h5>Three-letter language/country(region) codes</h5>
 *
 *
 * The Locale constructors have always specified that the language
 * and the country param be two characters in length, although in
 * practice they have accepted any length.  The specification has now
 * been relaxed to allow language codes of two to eight characters and
 * country (region) codes of two to three characters, and in
 * particular, three-letter language codes and three-digit region
 * codes as specified in the IANA Language Subtag Registry.  For
 * compatibility, the implementation still does not impose a length
 * constraint.
 *
 * @see Builder
 * @see ResourceBundle
 * @author Mark Davis
 * @author Wu Yuping (migrate to Kotlin)
 * @since Java 1.1
 */
actual class Locale {

    companion object {
        private val LOCALECACHE = Cache()

        /** Useful constant for language.
         */
        val ENGLISH = createConstant("en", "")

        /** Useful constant for language.
         */
        val FRENCH = createConstant("fr", "")

        /** Useful constant for language.
         */
        val GERMAN = createConstant("de", "")

        /** Useful constant for language.
         */
        val ITALIAN = createConstant("it", "")

        /** Useful constant for language.
         */
        val JAPANESE = createConstant("ja", "")

        /** Useful constant for language.
         */
        val KOREAN = createConstant("ko", "")

        /** Useful constant for language.
         */
        val CHINESE = createConstant("zh", "")

        /** Useful constant for language.
         */
        val SIMPLIFIED_CHINESE = createConstant("zh", "CN")

        /** Useful constant for language.
         */
        val TRADITIONAL_CHINESE = createConstant("zh", "TW")

        /** Useful constant for country.
         */
        val FRANCE = createConstant("fr", "FR")

        /** Useful constant for country.
         */
        val GERMANY = createConstant("de", "DE")

        /** Useful constant for country.
         */
        val ITALY = createConstant("it", "IT")

        /** Useful constant for country.
         */
        val JAPAN = createConstant("ja", "JP")

        /** Useful constant for country.
         */
        val KOREA = createConstant("ko", "KR")

        /** Useful constant for country.
         */
        val CHINA = SIMPLIFIED_CHINESE

        /** Useful constant for country.
         */
        val PRC = SIMPLIFIED_CHINESE

        /** Useful constant for country.
         */
        val TAIWAN = TRADITIONAL_CHINESE

        /** Useful constant for country.
         */
        val UK = createConstant("en", "GB")

        /** Useful constant for country.
         */
        val US = createConstant("en", "US")

        /** Useful constant for country.
         */
        val CANADA = createConstant("en", "CA")

        /** Useful constant for country.
         */
        val CANADA_FRENCH = createConstant("fr", "CA")

        /**
         * Useful constant for the root locale.  The root locale is the locale whose
         * language, country, and variant are empty ("") strings.  This is regarded
         * as the base locale of all locales, and is used as the language/country
         * neutral locale for the locale sensitive operations.
         *
         * @since Java 1.6
         */
        val ROOT = createConstant("", "")

        /**
         * The key for the private use extension ('x').
         *
         * @see .getExtension
         * @see Builder.setExtension
         * @since Java 1.7
         */
        const val PRIVATE_USE_EXTENSION = 'x'

        /**
         * The key for Unicode locale extension ('u').
         *
         * @see .getExtension
         * @see Builder.setExtension
         * @since Java 1.7
         */
        const val UNICODE_LOCALE_EXTENSION = 'u'

        /**
         * serialization ID
         */
        private val serialVersionUID = 9149081749638150636L

        private class Cache: LocaleObjectCache<Any, Locale>() {

            override fun createObject(key: Any): Locale? {
                return if (key is BaseLocale) {
                    Locale(key, null)
                } else {
                    val localeKey = key as LocaleKey
                    Locale(localeKey.base, localeKey.exts)
                }
            }
        }

        private class LocaleKey(internal val base: BaseLocale, internal val exts: LocaleExtensions?) {
            private val hash: Int
            init {
                val h = this.base.hashCode()
                this.hash = if (null != this.exts) h xor this.exts.hashCode()
                            else h
            }

            override fun equals(other: Any?): Boolean {
                if (other === this) {
                    return true
                }
                if (other !is LocaleKey) {
                    return false
                }
                return this.hash == other.hash && this.base == other.base &&
                        this.exts == other.exts
            }

            override fun hashCode(): Int {
                return this.hash
            }
        }

        /**
         * Display types for retrieving localized names from the name providers.
         */
        private const val DISPLAY_LANGUAGE = 0
        private const val DISPLAY_COUNTRY = 1
        private const val DISPLAY_VARIANT = 2
        private const val DISPLAY_SCRIPT = 3
        private const val DISPLAY_UEXT_KEY = 4
        private const val DISPLAY_UEXT_TYPE = 5

        /**
         * This method must be called only for creating the Locale.*
         * constants due to making shortcuts.
         */
        private fun createConstant(lang: String, country: String): Locale? {
            val base = BaseLocale.createInstance(lang, country)
            return getInstance(base, null)
        }

        /**
         * Returns a `Locale` constructed from the given
         * `language`, `country` and
         * `variant`. If the same `Locale` instance
         * is available in the cache, then that instance is
         * returned. Otherwise, a new `Locale` instance is
         * created and cached.
         *
         * @param language lowercase 2 to 8 language code.
         * @param country uppercase two-letter ISO-3166 code and numeric-3 UN M.49 area code.
         * @param variant vendor and browser specific code. See class description.
         * @return the `Locale` instance requested
         * @exception NullPointerException if any argument is null.
         */
        internal fun getInstance(language: String, country: String, variant: String?): Locale? {
            return getInstance(language, "", country, variant, null)
        }

        internal fun getInstance(language: String?, script: String?, country: String?,
                                      variant: String?, extensions: LocaleExtensions?): Locale? {
            if (language== null || script == null || country == null || variant == null) {
                throw NullPointerException()
            }

            val _extensions = extensions ?: getCompatibilityExtensions(language, script, country, variant)

            val baseloc = BaseLocale.getInstance(language, script, country, variant)
            return getInstance(baseloc!!, _extensions)
        }

        internal fun getInstance(baseloc: BaseLocale, extensions: LocaleExtensions?): Locale? {
            return if (extensions == null) {
                LOCALECACHE.get(baseloc)
            } else {
                val key = LocaleKey(baseloc, extensions)
                LOCALECACHE.get(key)
            }
        }

        var defaultLocale: Locale? = null
        var defaultDisplayLocale: Locale? = null
        var defaultFormatLocale: Locale? = null

        fun getDefault(): Locale {
            if (null == defaultLocale) {
                if (js("typeof(navigator) === 'object'")) {
                    defaultLocale = getDefaultInBrowser()
                }
            }
            if (null == defaultLocale) {
                defaultLocale = ENGLISH
            }
            return defaultLocale!!
        }

        private fun getDefaultInBrowser(): Locale? {
            val language = js("navigator && (navigator.userLanguage || navigator.language ||" +
                    "navigator.browserLanguage || navigator.systemLanguage) && null") as String?
            if (language != null) {
                val langCountry = Regex("([a-z]{2})-([A-Z]{2})").find(language)
                if (langCountry != null) {
                    return getInstance(langCountry.groupValues[1], langCountry.groupValues[2], null)
                }
            }
            return null
        }

        fun getDefaultByCategory(category: Category): Locale {
            /*
            return when (category) {
                Category.DISPLAY -> {
                    if (defaultDisplayLocale == null) {
                        defaultDisplayLocale = initDefault(category)
                    }
                    defaultDisplayLocale!!
                }
                Category.FORMAT -> {
                    if (defaultFormatLocale == null) {
                        defaultFormatLocale = initDefault(category)
                    }
                    defaultFormatLocale!!
                }
                else -> throw AssertionError("Unknown Category")
            }
            */
            return getDefault()
        }

        /**
         * Enum for locale categories.  These locale categories are used to get/set
         * the default locale for the specific functionality represented by the
         * category.
         *
         * @see .getDefault
         * @see .setDefault
         * @since Java 1.7
         */
        enum class Category constructor(
            internal val languageKey: String, internal val scriptKey: String, internal val countryKey: String,
            internal val variantKey: String, internal val extensionsKey: String
        ) {

            /**
             * Category used to represent the default locale for
             * displaying user interfaces.
             */
            DISPLAY(
                "user.language.display",
                "user.script.display",
                "user.country.display",
                "user.variant.display",
                "user.extensions.display"
            ),

            /**
             * Category used to represent the default locale for
             * formatting dates, numbers, and/or currencies.
             */
            FORMAT(
                "user.language.format",
                "user.script.format",
                "user.country.format",
                "user.variant.format",
                "user.extensions.format"
            )
        }

        /**
         * This enum provides constants to select a filtering mode for locale
         * matching. Refer to [RFC 4647 Matching of Language Tags](http://tools.ietf.org/html/rfc4647) for details.
         *
         *
         * As an example, think of two Language Priority Lists each of which
         * includes only one language range and a set of following language tags:
         *
         * <pre>
         * de (German)
         * de-DE (German, Germany)
         * de-Deva (German, in Devanagari script)
         * de-Deva-DE (German, in Devanagari script, Germany)
         * de-DE-1996 (German, Germany, orthography of 1996)
         * de-Latn-DE (German, in Latin script, Germany)
         * de-Latn-DE-1996 (German, in Latin script, Germany, orthography of 1996)
         * </pre>
         *
         * The filtering method will behave as follows:
         *
         * <table class="striped">
         * <caption>Filtering method behavior</caption>
         * <thead>
         * <tr>
         * <th scope="col">Filtering Mode</th>
         * <th scope="col">Language Priority List: `"de-DE"`</th>
         * <th scope="col">Language Priority List: `"de-*-DE"`</th>
         * </tr>
         * </thead>
         * <tbody>
         * <tr>
         * <th scope="row" style="vertical-align:top">
         * [AUTOSELECT_FILTERING][FilteringMode.AUTOSELECT_FILTERING]
         * </th>
         * <td style="vertical-align:top">
         * Performs *basic* filtering and returns `"de-DE"` and
         * `"de-DE-1996"`.
         * </td>
         * <td style="vertical-align:top">
         * Performs *extended* filtering and returns `"de-DE"`,
         * `"de-Deva-DE"`, `"de-DE-1996"`, `"de-Latn-DE"`, and
         * `"de-Latn-DE-1996"`.
         * </td>
         * </tr>
         * <tr>
         * <th scope="row" style="vertical-align:top">
         * [EXTENDED_FILTERING][FilteringMode.EXTENDED_FILTERING]
         * </th>
         * <td style="vertical-align:top">
         * Performs *extended* filtering and returns `"de-DE"`,
         * `"de-Deva-DE"`, `"de-DE-1996"`, `"de-Latn-DE"`, and
         * `"de-Latn-DE-1996"`.
         * </td>
         * <td style="vertical-align:top">Same as above.</td>
         * </tr>
         * <tr>
         * <th scope="row" style="vertical-align:top">
         * [IGNORE_EXTENDED_RANGES][FilteringMode.IGNORE_EXTENDED_RANGES]
         * </th>
         * <td style="vertical-align:top">
         * Performs *basic* filtering and returns `"de-DE"` and
         * `"de-DE-1996"`.
         * </td>
         * <td style="vertical-align:top">
         * Performs *basic* filtering and returns `null` because
         * nothing matches.
         * </td>
         * </tr>
         * <tr>
         * <th scope="row" style="vertical-align:top">
         * [MAP_EXTENDED_RANGES][FilteringMode.MAP_EXTENDED_RANGES]
         * </th>
         * <td style="vertical-align:top">Same as above.</td>
         * <td style="vertical-align:top">
         * Performs *basic* filtering and returns `"de-DE"` and
         * `"de-DE-1996"` because `"de-*-DE"` is mapped to
         * `"de-DE"`.
         * </td>
         * </tr>
         * <tr>
         * <th scope="row" style="vertical-align:top">
         * [REJECT_EXTENDED_RANGES][FilteringMode.REJECT_EXTENDED_RANGES]
         * </th>
         * <td style="vertical-align:top">Same as above.</td>
         * <td style="vertical-align:top">
         * Throws [IllegalArgumentException] because `"de-*-DE"` is
         * not a valid basic language range.
         * </td>
         * </tr>
         * </tbody>
         * </table>
         *
         * @see .filter
         * @see .filterTags
         * @since Java 1.8
         */
        enum class FilteringMode {
            /**
             * Specifies automatic filtering mode based on the given Language
             * Priority List consisting of language ranges. If all of the ranges
             * are basic, basic filtering is selected. Otherwise, extended
             * filtering is selected.
             */
            AUTOSELECT_FILTERING,

            /**
             * Specifies extended filtering.
             */
            EXTENDED_FILTERING,

            /**
             * Specifies basic filtering: Note that any extended language ranges
             * included in the given Language Priority List are ignored.
             */
            IGNORE_EXTENDED_RANGES,

            /**
             * Specifies basic filtering: If any extended language ranges are
             * included in the given Language Priority List, they are mapped to the
             * basic language range. Specifically, a language range starting with a
             * subtag `"*"` is treated as a language range `"*"`. For
             * example, `"*-US"` is treated as `"*"`. If `"*"` is
             * not the first subtag, `"*"` and extra `"-"` are removed.
             * For example, `"ja-*-JP"` is mapped to `"ja-JP"`.
             */
            MAP_EXTENDED_RANGES,

            /**
             * Specifies basic filtering: If any extended language ranges are
             * included in the given Language Priority List, the list is rejected
             * and the filtering method throws [IllegalArgumentException].
             */
            REJECT_EXTENDED_RANGES
        }

        private var isoLanguages: Array<String>? = null

        private var isoCountries: Array<String>? = null

        private fun convertOldISOCodes(language: String): String {
            // we accept both the old and the new ISO codes for the languages whose ISO
            // codes have changed, but we always store the OLD code, for backward compatibility
            var _language = internString(LocaleUtils.toLowerString(language))
            return if (_language === "he") {
                "iw"
            } else if (_language === "yi") {
                "ji"
            } else if (_language === "id") {
                "in"
            } else {
                _language
            }
        }

        private fun getCompatibilityExtensions(
            language: String,
            script: String,
            country: String,
            variant: String
        ): LocaleExtensions? {
            var extensions: LocaleExtensions? = null
            // Special cases for backward compatibility support
            if (LocaleUtils.caseIgnoreMatch(language, "ja")
                && script.isEmpty()
                && LocaleUtils.caseIgnoreMatch(country, "jp")
                && "JP" == variant
            ) {
                // ja_JP_JP -> u-ca-japanese (calendar = japanese)
                extensions = LocaleExtensions.CALENDAR_JAPANESE
            } else if (LocaleUtils.caseIgnoreMatch(language, "th")
                && script.isEmpty()
                && LocaleUtils.caseIgnoreMatch(country, "th")
                && "TH" == variant
            ) {
                // th_TH_TH -> u-nu-thai (numbersystem = thai)
                extensions = LocaleExtensions.NUMBER_THAI
            }
            return extensions
        }

        /**
         * Format a list using given pattern strings.
         * If either of the patterns is null, then a the list is
         * formatted by concatenation with the delimiter ','.
         * @param stringList the list of strings to be formatted.
         * and formatting them into a list.
         * @param pattern should take 2 arguments for reduction
         * @return a string representing the list.
        private fun formatList(stringList: Array<String>, pattern: String?): String {
            // If we have no list patterns, compose the list in a simple,
            // non-localized way.
            if (pattern == null) {
                return stringList.joinToString(",")
            }

            return when (stringList.size) {
                0 -> ""
                1 -> stringList[0]
                else ->
                    stringList.asList().reduce {s1, s2 ->
                        return if (s1 == "") s2
                        else if (s2 == "")  s1
                        else MessageFormat.format(pattern, s1, s2)
                    }
            }
        }
         */

        // Duplicate of sun.util.locale.UnicodeLocaleExtension.isKey in order to
        // avoid its class loading.
        private fun isUnicodeExtensionKey(s: String): Boolean {
            // 2alphanum
            return s.length == 2 && LocaleUtils.isAlphaNumericString(s)
        }
    } // companion object

    /**
     * Private constructor used by getInstance method
     */
    private constructor(baseLocale: BaseLocale?, extensions: LocaleExtensions?) {
        this.baseLocale = baseLocale
        this.localeExtensions = extensions
    }

    /**
     * Construct a locale from language, country and variant.
     * This constructor normalizes the language value to lowercase and
     * the country value to uppercase.
     *
     *
     * **Note:**
     *
     *  * ISO 639 is not a stable standard; some of the language codes it defines
     * (specifically "iw", "ji", and "in") have changed.  This constructor accepts both the
     * old codes ("iw", "ji", and "in") and the new codes ("he", "yi", and "id"), but all other
     * API on Locale will return only the OLD codes.
     *  * For backward compatibility reasons, this constructor does not make
     * any syntactic checks on the input.
     *  * The two cases ("ja", "JP", "JP") and ("th", "TH", "TH") are handled specially,
     * see [Special Cases](#special_cases_constructor) for more information.
     *
     *
     * @param language An ISO 639 alpha-2 or alpha-3 language code, or a language subtag
     * up to 8 characters in length.  See the `Locale` class description about
     * valid language values.
     * @param country An ISO 3166 alpha-2 country code or a UN M.49 numeric-3 area code.
     * See the `Locale` class description about valid country values.
     * @param variant Any arbitrary value used to indicate a variation of a `Locale`.
     * See the `Locale` class description for the details.
     * @exception NullPointerException thrown if any argument is null.
     */
    actual constructor(language: String?, country: String?, variant: String?) {
        if (language == null || country == null || variant == null) {
            throw NullPointerException()
        }
        this.baseLocale = BaseLocale.getInstance(convertOldISOCodes(language), "", country, variant)
        this.localeExtensions = getCompatibilityExtensions(language, "", country, variant)
    }

    /**
     * Construct a locale from language and country.
     * This constructor normalizes the language value to lowercase and
     * the country value to uppercase.
     *
     *
     * **Note:**
     *
     *  * ISO 639 is not a stable standard; some of the language codes it defines
     * (specifically "iw", "ji", and "in") have changed.  This constructor accepts both the
     * old codes ("iw", "ji", and "in") and the new codes ("he", "yi", and "id"), but all other
     * API on Locale will return only the OLD codes.
     *  * For backward compatibility reasons, this constructor does not make
     * any syntactic checks on the input.
     *
     *
     * @param language An ISO 639 alpha-2 or alpha-3 language code, or a language subtag
     * up to 8 characters in length.  See the `Locale` class description about
     * valid language values.
     * @param country An ISO 3166 alpha-2 country code or a UN M.49 numeric-3 area code.
     * See the `Locale` class description about valid country values.
     * @exception NullPointerException thrown if either argument is null.
     */
    actual constructor(language: String, country: String): this(language, country, "")

    /**
     * Construct a locale from a language code.
     * This constructor normalizes the language value to lowercase.
     *
     *
     * **Note:**
     *
     *  * ISO 639 is not a stable standard; some of the language codes it defines
     * (specifically "iw", "ji", and "in") have changed.  This constructor accepts both the
     * old codes ("iw", "ji", and "in") and the new codes ("he", "yi", and "id"), but all other
     * API on Locale will return only the OLD codes.
     *  * For backward compatibility reasons, this constructor does not make
     * any syntactic checks on the input.
     *
     *
     * @param language An ISO 639 alpha-2 or alpha-3 language code, or a language subtag
     * up to 8 characters in length.  See the `Locale` class description about
     * valid language values.
     * @exception NullPointerException thrown if argument is null.
     * @since Java 1.4
     */
    actual constructor(language: String): this(language, "", "")

    /**
     * Returns the language code of this Locale.
     *
     *
     * **Note:** ISO 639 is not a stable standard some languages' codes have changed.
     * Locale's constructor recognizes both the new and the old codes for the languages
     * whose codes have changed, but this function always returns the old code.  If you
     * want to check for a specific language whose code has changed, don't do
     * <pre>
     * if (locale.getLanguage().equals("he")) // BAD!
     * ...
     * ```
     * Instead, do
     * ```
     * if (locale.getLanguage().equals(new Locale("he").getLanguage()))
     * ...
     * ```
     * @return The language code, or the empty string if none is defined.
     * @see .getDisplayLanguage
     */
    actual fun getLanguage(): String {
        return baseLocale!!.language
    }

    /**
     * Returns the script for this locale, which should
     * either be the empty string or an ISO 15924 4-letter script
     * code. The first letter is uppercase and the rest are
     * lowercase, for example, 'Latn', 'Cyrl'.
     *
     * @return The script code, or the empty string if none is defined.
     * @see .getDisplayScript
     *
     * @since Java 1.7
     */
    actual fun getScript(): String {
        return baseLocale!!.script
    }

    /**
     * Returns the country/region code for this locale, which should
     * either be the empty string, an uppercase ISO 3166 2-letter code,
     * or a UN M.49 3-digit code.
     *
     * @return The country/region code, or the empty string if none is defined.
     * @see .getDisplayCountry
     */
    actual fun getCountry(): String {
        return baseLocale!!.region
    }

    /**
     * Returns the variant code for this locale.
     *
     * @return The variant code, or the empty string if none is defined.
     * @see .getDisplayVariant
     */
    actual fun getVariant(): String {
        return baseLocale!!.variant
    }

    private val baseLocale: BaseLocale?
    private val localeExtensions: LocaleExtensions?

    /**
     * Calculated hashcode
     */
    private var hashCodeValue: Int = 0

    private var languageTag: String? = null

    override fun toString(): String {
        val baseLocale = this.baseLocale!!
        val l = baseLocale.language.isNotEmpty()
        val s = baseLocale.script.isNotEmpty()
        val r = baseLocale.region.isNotEmpty()
        val v = baseLocale.variant.isNotEmpty()
        val e = this.localeExtensions != null && this.localeExtensions.id.isNotEmpty()

        val result = StringBuilder().append(baseLocale.language)
        if (r || l && (v || s || e)) {
            result.append('_').append(baseLocale.region) // This may just append '_'
        }
        if (v && (l || r)) {
            result.append('_').append(baseLocale.variant)
        }

        if (s && (l || r)) {
            result.append("_#").append(baseLocale.script)
        }

        if (e && (l || r)) {
            result.append('_')
            if (!s) {
                result.append('#')
            }
            result.append(this.localeExtensions!!.id)
        }

        return result.toString()
    }

    /**
     * Returns a well-formed IETF BCP 47 language tag representing
     * this locale.
     *
     *
     * If this `Locale` has a language, country, or
     * variant that does not satisfy the IETF BCP 47 language tag
     * syntax requirements, this method handles these fields as
     * described below:
     *
     *
     * **Language:** If language is empty, or not [well-formed](#def_language) (for example "a" or
     * "e2"), it will be emitted as "und" (Undetermined).
     *
     *
     * **Country:** If country is not [well-formed](#def_region) (for example "12" or "USA"),
     * it will be omitted.
     *
     *
     * **Variant:** If variant **is** [well-formed](#def_variant), each sub-segment
     * (delimited by '-' or '_') is emitted as a subtag.  Otherwise:
     *
     *
     *  * if all sub-segments match `[0-9a-zA-Z]{1,8}`
     * (for example "WIN" or "Oracle_JDK_Standard_Edition"), the first
     * ill-formed sub-segment and all following will be appended to
     * the private use subtag.  The first appended subtag will be
     * "lvariant", followed by the sub-segments in order, separated by
     * hyphen. For example, "x-lvariant-WIN",
     * "Oracle-x-lvariant-JDK-Standard-Edition".
     *
     *  * if any sub-segment does not match
     * `[0-9a-zA-Z]{1,8}`, the variant will be truncated
     * and the problematic sub-segment and all following sub-segments
     * will be omitted.  If the remainder is non-empty, it will be
     * emitted as a private use subtag as above (even if the remainder
     * turns out to be well-formed).  For example,
     * "Solaris_isjustthecoolestthing" is emitted as
     * "x-lvariant-Solaris", not as "solaris".
     *
     *
     * **Special Conversions:** Java supports some old locale
     * representations, including deprecated ISO language codes,
     * for compatibility. This method performs the following
     * conversions:
     *
     *
     *  * Deprecated ISO language codes "iw", "ji", and "in" are
     * converted to "he", "yi", and "id", respectively.
     *
     *  * A locale with language "no", country "NO", and variant
     * "NY", representing Norwegian Nynorsk (Norway), is converted
     * to a language tag "nn-NO".
     *
     *
     * **Note:** Although the language tag created by this
     * method is well-formed (satisfies the syntax requirements
     * defined by the IETF BCP 47 specification), it is not
     * necessarily a valid BCP 47 language tag.  For example,
     * <pre>
     * new Locale("xx", "YY").toLanguageTag();</pre>
     *
     * will return "xx-YY", but the language subtag "xx" and the
     * region subtag "YY" are invalid because they are not registered
     * in the IANA Language Subtag Registry.
     *
     * @return a BCP47 language tag representing the locale
     * @see .forLanguageTag
     * @since Java 1.7
     */
    actual fun toLanguageTag(): String {
        var languageTag = this.languageTag
        if (languageTag != null) {
            return languageTag
        }

        val tag = LanguageTag.parseLocale(this.baseLocale!!, this.localeExtensions)
        val buf = StringBuilder()

        var subtag = tag.getLanguage()
        if (subtag.isNotEmpty()) {
            buf.append(LanguageTag.canonicalizeLanguage(subtag))
        }

        subtag = tag.getScript()
        if (subtag.isNotEmpty()) {
            buf.append(LanguageTag.SEP)
            buf.append(LanguageTag.canonicalizeScript(subtag))
        }

        subtag = tag.getRegion()
        if (subtag.isNotEmpty()) {
            buf.append(LanguageTag.SEP)
            buf.append(LanguageTag.canonicalizeRegion(subtag))
        }

        var subtags = tag.getVariants()
        for (s in subtags) {
            buf.append(LanguageTag.SEP)
            // preserve casing
            buf.append(s)
        }

        subtags = tag.getExtensions()
        for (s in subtags) {
            buf.append(LanguageTag.SEP)
            buf.append(LanguageTag.canonicalizeExtension(s))
        }

        subtag = tag.getPrivateuse()
        if (subtag.isNotEmpty()) {
            if (buf.isNotEmpty()) {
                buf.append(LanguageTag.SEP)
            }
            buf.append(LanguageTag.PRIVATEUSE).append(LanguageTag.SEP)
            // preserve casing
            buf.append(subtag)
        }

        val langTag = buf.toString()
        org.firas.lang.synchronized(this) {
            if (this.languageTag == null) {
                this.languageTag = langTag
            }
        }
        return this.languageTag!!
    }

    override fun hashCode(): Int {
        var hc = this.hashCodeValue
        if (hc == 0) {
            hc = this.baseLocale.hashCode()
            if (this.localeExtensions != null) {
                hc = hc xor this.localeExtensions.hashCode()
            }
            this.hashCodeValue = hc
        }
        return hc
    }

    override fun equals(other: Any?): Boolean {
         if (this === other) {                    // quick check
             return true
         }
        if (other !is Locale) {
            return false
        }
        return this.baseLocale == other.baseLocale && this.localeExtensions == other.localeExtensions
    }

    /**
     * Return an array of the display names of the variant.
     * @param bundle the ResourceBundle to use to get the display names
     * @return an array of display names, possible of zero length.
    private fun getDisplayVariantArray(inLocale: Locale): Array<String> {
        // Split the variant name into tokens separated by '_'.
        val tokenizer = StringTokenizer(this.baseLocale!!.variant, "_")
        return Array<String>(tokenizer.countTokens()) { i ->
            // For each variant token, lookup the display name.  If
            // not found, use the variant name itself.
            getDisplayString(
                tokenizer.next(), null,
                inLocale, DISPLAY_VARIANT
            )
        }
    }
     */

    /*
    private fun getDisplayString(code: String, cat: String, inLocale: Locale, type: Int): String {
        if (code.isEmpty()) {
            return ""
        }

        val pool = LocaleServiceProviderPool.getPool(LocaleNameProvider::class)
        val rbKey = if (type == DISPLAY_VARIANT) "%%"+code else code
        val result = pool.getLocalizedObject(
                                LocaleNameGetter.INSTANCE,
                                inLocale, rbKey, type, code, cat)
        return result ?: code
    }
    */

    /*
    private fun getDisplayKeyTypeExtensionString(key: String, lr: LocaleResources, inLocale: Locale): String? {
        val type = this.localeExtensions!!.getUnicodeLocaleType(key)
        var ret = getDisplayString(type, key, inLocale, DISPLAY_UEXT_TYPE)

        if (ret == null || ret == type) {
            // no localization for this type. try combining key/type separately
            var displayType: String = type
            when (key) {
                "cu" -> displayType = lr.getCurrencyName(type!!.toLowerCase(Locale.ROOT))
                "rg" -> if (type != null &&
                    // UN M.49 code should not be allowed here
                    type.matches("^[a-zA-Z]{2}[zZ]{4}$".toRegex())
                ) {
                    displayType = lr.getLocaleName(type.substring(0, 2).toUpperCase(Locale.ROOT))
                }
                "tz" -> displayType = TimeZoneNameUtility.convertLDMLShortID(type)
                    .map({ id -> TimeZoneNameUtility.retrieveGenericDisplayName(id, TimeZone.LONG, inLocale) })
                    .orElse(type)
            }
            ret = MessageFormat.format(
                lr.getLocaleName("ListKeyTypePattern"),
                getDisplayString(key, null, inLocale, DISPLAY_UEXT_KEY),
                Optional.ofNullable(displayType).orElse(type)
            )
        }

        return ret
    }
    */


}

actual typealias LocaleCategory = Locale.Companion.Category

actual fun getDefaultLocale(): Locale = Locale.getDefault()
actual fun getDefaultLocale(category: LocaleCategory): Locale = Locale.getDefaultByCategory(category)
