package tech.firas.text

/**
 *
 * @author Wu Yuping (migrate from Java 11)
 */
expect class ParsePosition {
    constructor(index: Int)

    fun getIndex(): Int
    fun setIndex(index: Int)
    fun getErrorIndex(): Int
    fun setErrorIndex(errorIndex: Int)
}