package fundamentals

class Recursion {

    fun factorial(n: Int): Int =
            if (n == 0) 1
            else n * factorial(n - 1)

    fun factorialTail(n: Int): Int {
        tailrec fun loop(acc: Int, n: Int): Int =
                if (n == 0) acc
                else loop(acc * n, n - 1)
        return loop(1, n)
    }

    fun pascal(c: Int, r: Int): Int =
            if (c == 0 || r == c) 1
            else pascal(c, r - 1) + pascal(c - 1, r - 1)

    fun balance(chars: List<Char>): Boolean {
        fun balanced(chars: List<Char>, open: Int): Boolean =
                when {
                    chars.isEmpty() -> open == 0
                    chars.head == '(' -> balanced(chars.tail, open + 1)
                    chars.head == ')' -> open > 0 && balanced(chars.tail, open - 1)
                    else -> balanced(chars.tail, open)
                }
        return balanced(chars, 0)
    }

    tailrec fun <T> findNthElement(n: Int, xs: List<T>): T =
            if (n == 0) xs.head
            else findNthElement(n - 1, xs.tail)

    tailrec fun findFixPoint(x: Double = 1.0): Double =
            if (x == Math.cos(x)) x
            else findFixPoint(Math.cos(x))

    fun listLength(arr: List<Int>): Int {
        fun loop(a: Int, arr: List<Int>): Int =
                if (arr.isEmpty()) a
                else loop(a + 1, arr.tail)

        return loop(0, arr)
    }

    private val <T> List<T>.tail: List<T>
        get() = drop(1)

    private val <T> List<T>.head: T
        get() = first()
}
