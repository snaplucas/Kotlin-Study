class Recursion {

    fun factorial(n: Int): Int =
            if (n == 0) 1
            else n * factorial(n - 1)

    fun factorialTail(n: Int) = {
        fun loop(acc: Int, n: Int): Int =
                if (n == 0) acc
                else loop(acc * n, n - 1)

        loop(1, n)
    }

    fun pascal(c: Int, r: Int): Int =
            if (c == 0 || r == c) 1
            else pascal(c, r - 1) + pascal(c - 1, r - 1)

    fun balance(chars: List<Char>): Boolean {
        fun balanced(chars: List<Char>, open: Int): Boolean =
                if (chars.isEmpty()) open == 0
                else if (chars.head == '(') balanced(chars.tail, open + 1)
                else if (chars.head == ')') open > 0 && balanced(chars.tail, open - 1)
                else balanced(chars.tail, open)

        return balanced(chars, 0)
    }

    val <T> List<T>.tail: List<T>
        get() = drop(1)

    val <T> List<T>.head: T
        get() = first()
}
