package org.firas.lang

import kotlin.reflect.KClass

/**
 *
 * @author Wu Yuping
 */
actual fun <T: Any> KClass<T>.getName(): String {
    return this.simpleName?:this.js.name // this.qualifiedName
}

actual fun <T: Any> KClass<T>.isAssignableFrom(descendent: KClass<*>): Boolean {
    TODO("Not implemented")
}
