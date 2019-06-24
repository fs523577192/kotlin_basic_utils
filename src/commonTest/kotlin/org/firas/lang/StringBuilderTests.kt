package org.firas.lang

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 *
 * @author Wu Yuping
 */
class StringBuilderTests {

    @Test
    fun testSetLength() {
        val builder = StringBuilder().append("123456789")
        val shortLength = Random.nextInt(0, 9)
        println("shortLength: $shortLength")
        builder.setLength(shortLength)
        assertEquals(shortLength, builder.length)

        val longLength = Random.nextInt(9, 20)
        println("longLength: $longLength")
        builder.setLength(longLength)
        assertEquals(longLength, builder.length)
    }

    @Test
    fun testInsert() {
        for (j in 0..1000) {
            val builder1 = StringBuilder()
            for (i in 0..Random.nextInt(0, 1000)) {
                builder1.append('A' + Random.nextInt(0, 26))
            }
            val builder2 = StringBuilder()
            for (i in 0..Random.nextInt(0, 1000)) {
                builder2.append('0' + Random.nextInt(0, 10))
            }
            println("builder1: \"$builder1\".length is " + builder1.length)
            println("builder2: \"$builder2\".length is " + builder2.length)

            val index = Random.nextInt(0, builder1.length + 1)
            builder1.insert(index, builder2)
            assertEquals(index, builder1.indexOf(builder2.toString()))
        }
    }

    @Test
    fun testReplace() {
        for (j in 0..1000) {
            val builder1 = StringBuilder()
            for (i in 0..Random.nextInt(0, 1000)) {
                builder1.append('A' + Random.nextInt(0, 26))
            }
            val builder2 = StringBuilder()
            for (i in 0..Random.nextInt(0, 1000)) {
                builder2.append('0' + Random.nextInt(0, 10))
            }
            println("builder1: \"$builder1\".length is " + builder1.length)
            println("builder2: \"$builder2\".length is " + builder2.length)

            val originalLength = builder1.length

            val indexBegin = Random.nextInt(0, builder1.length - 1)
            val indexEnd = Random.nextInt(indexBegin, builder1.length)
            println("indexBegin: $indexBegin, indexEnd: $indexEnd")
            println(builder1.replace(indexBegin, indexEnd, builder2))
            assertEquals(originalLength - indexEnd + indexBegin + builder2.length, builder1.length)
            assertEquals(indexBegin, builder1.indexOf(builder2.toString()))
        }
    }
}