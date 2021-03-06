/*
 * Copyright (c) 2003, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.firas.lang

import kotlin.js.JsName

@JsName("StringBuilder_checkOffset")
internal fun checkOffset(offset:Int, length:Int) {
    if (offset < 0 || offset > length) {
        throw StringIndexOutOfBoundsException("offset $offset, length $length")
    }
}

/**
 * Supplement of kotlin.text.StringBuilder
 * @author Wu Yuping
 */
@JsName("StringBuilder_setLength")
fun StringBuilder.setLength(length: Int) {
    if (this.length < length) {
        for (i in 0 .. length - this.length) {
            this.append(0.toChar())
        }
    } else if (this.length > length) {
        this.removeRange(length, this.length)
    }
}

/**
 * Supplement of kotlin.text.StringBuilder
 * @author Wu Yuping
 */
@JsName("StringBuilder_insert")
fun StringBuilder.insert(index: Int, charSequence: CharSequence): StringBuilder {
    checkOffset(index, this.length)
    val temp1 = this.subSequence(0, index)
    val temp2 = this.subSequence(index, this.length)
    return this.clear().append(temp1).append(charSequence).append(temp2)
}

/**
 * Supplement of kotlin.text.StringBuilder
 * @author Wu Yuping
 */
@JsName("StringBuilder_replaceRange")
fun StringBuilder.replace(indexBegin: Int, indexEnd: Int, charSequence: CharSequence): StringBuilder {
    if (indexBegin > indexEnd) {
        throw IllegalArgumentException("indexBegin ($indexBegin) must not be greater than indexEnd ($indexEnd)")
    }
    checkOffset(indexBegin, this.length)
    checkOffset(indexEnd, this.length)
    val temp1 = this.subSequence(0, indexBegin)
    val temp2 = this.subSequence(indexEnd, this.length)
    return this.clear().append(temp1).append(charSequence).append(temp2)
}
