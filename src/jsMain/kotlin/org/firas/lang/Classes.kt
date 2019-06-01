package org.firas.lang

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
    TODO("Not implemented")
}
