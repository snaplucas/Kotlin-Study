package coroutines

import org.junit.Test

class FooTest {

    @Test
    fun foo() {
        val list = "aaabbbcc".toList()

        val testeA = list.groupingBy { it }.eachCount()
        val testeB = list.groupBy { it }

        println(testeA)
        println(testeB)
    }

    @Test
    fun anotherFoo() {
        val fruits = listOf("apple", "banana", "orange")
        println(fruits.map { it.toUpperCase() })
    }

    fun anagram(s1: String, s2: String) = (s1.length + s2.length) - (s1.toList().intersect(s2.toList()).size * 2)
}