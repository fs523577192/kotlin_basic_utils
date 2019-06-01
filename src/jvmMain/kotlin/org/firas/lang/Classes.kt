package org.firas.lang

import kotlin.reflect.KClass

/**
 *
 * @author Wu Yuping
 */
actual fun <T: Any> KClass<T>.getName(): String {
    return this.java.name
}

actual fun <T: Any> KClass<T>.isAssignableFrom(descendent: KClass<*>): Boolean {
    return this.java.isAssignableFrom(descendent.java)
}
