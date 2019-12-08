package tech.firas.lang

import kotlin.js.JsName
import kotlin.reflect.KClass

/**
 *
 * @author Wu Yuping
 */
@JsName("KClass_getName")
actual fun <T: Any> KClass<T>.getName(): String {
    return this.simpleName?:this.js.name // this.qualifiedName
}

@JsName("KClass_isAssignableFrom")
actual fun <T: Any> KClass<T>.isAssignableFrom(descendent: KClass<*>): Boolean {
    // handle primitive
    if (this == descendent) {
        return true
    }
    when (this) {
        Any::class ->
            return true
        Number::class ->
            when (descendent) {
                Byte::class, Short::class, Int::class, Long::class ->
                    return true
            }
        CharSequence::class ->
            when (descendent) {
                String::class ->
                    return true
            }
    }

    val descendent_js = descendent.js.asDynamic()
    val baseClasses = descendent_js["\$metadata\$"].interfaces.unsafeCast<Array<JsClass<*>>>()
    return baseClasses.any {
        this.isAssignableFrom(it.kotlin)
    }
}
