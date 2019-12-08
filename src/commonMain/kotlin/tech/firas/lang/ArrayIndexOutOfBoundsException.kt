package tech.firas.lang

/**
 *
 * @author Wu Yuping
 */
expect class ArrayIndexOutOfBoundsException: IndexOutOfBoundsException {

    constructor()

    constructor(s: String)

    constructor(index: Int)
}