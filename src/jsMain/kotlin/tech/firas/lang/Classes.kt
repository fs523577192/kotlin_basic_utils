/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tech.firas.lang

import kotlin.js.JsName
import kotlin.reflect.KClass

@JsName("KClass_getName")
actual fun <T: Any> KClass<T>.getName(): String {
    return this.simpleName?:this.js.name // this.qualifiedName
}

/**
 *
 * @author Wu Yuping
 */
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
