/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

/*
 * (C) Copyright Taligent, Inc. 1996, 1997 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 */
package org.firas.text

import org.firas.lang.getName

/**
 * `ParsePosition` is a simple class used by `Format` and its
 * subclasses to keep track of the current position during parsing.
 * The `parseObject` method in the various `Format`
 * classes requires a `ParsePosition` object as an argument.
 *
 *
 * By design, as you parse through a string with different formats,
 * you can use the same `ParsePosition`, since the index parameter
 * records the current position.
 *
 * @author Mark Davis
 * @author Wu Yuping (migrate from Java 11)
 * @since Java 1.1
 * @see   java.text.Format
 */
class ParsePosition(
    /**
     * Input: the place you start parsing.
     *
     * Output: position where the parse stopped.
     * This is designed to be used serially,
     * with each call setting index up for the next one.
     */
    var index: Int
) {

    /**
     * The index at which an error occurred, or -1 if the
     * error index has not been set.
     *
     * @since Java 1.2
     */
    var errorIndex: Int = -1

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is ParsePosition) {
            return false
        }
        return this.index == other.index && this.errorIndex == other.errorIndex
    }

    override fun hashCode(): Int {
        return this.errorIndex.shl(16) or this.index
    }

    override fun toString(): String {
        return this::class.getName() +
                "[index=" + this.index +
                ",errorIndex=" + this.errorIndex + ']'
    }
}
