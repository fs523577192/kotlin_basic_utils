package org.firas.promise

/**
 *
 * @author Wu Yuping
 */
class IllegalPromiseStateException: Exception {

    constructor(): super()

    constructor(message: String): super(message)
}