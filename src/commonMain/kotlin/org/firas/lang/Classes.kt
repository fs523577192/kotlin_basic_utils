package org.firas.lang

import kotlin.reflect.KClass

/**
 *
 * @author Wu Yuping
 */
expect fun <T: Any> KClass<T>.getName(): String

expect fun <T: Any> KClass<T>.isAssignableFrom(descendent: KClass<*>): Boolean