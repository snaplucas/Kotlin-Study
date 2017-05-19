class Functions {

    fun some(a: Int) = a > 10

    fun foo(x: Int, p: (Int) -> Boolean) = if (p(x)) println("maior") else println("menor")

    fun blah(a: Int) = foo(a, { x -> x > 10 })

    fun bleh(a: Int): (Int) -> Boolean = { x -> x == a }

    fun poff(a: Int) = foo(a, { some(it) })

}