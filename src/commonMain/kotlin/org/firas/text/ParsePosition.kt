package org.firas.text

/**
 *
 * @author Wu Yuping
 */
expect class ParsePosition {
    constructor(index: Int)

    fun getIndex(): Int
    fun setIndex(index: Int)
    fun getErrorIndex(): Int
    fun setErrorIndex(errorIndex: Int)
}