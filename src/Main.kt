fun main(args: Array<String>) {
    val recursion = Recursion()

    println(recursion.balance("(if (zero? x) max (/ 1 x))".toList()))

    println(recursion.factorialTail(4).invoke())
    println(recursion.pascal(2, 2))
    val lista = "abcd".toList()
    val a = lista.map { x -> x + "x" }

    println(a)

    val functions = Functions()
    functions.blah(50)

    fun isOdd(x: Int) = x % 2 != 0

    val numbers = listOf(1, 2, 3)
    println(numbers.filter(::isOdd))

    fun rotate(s: String) = s.drop(1) + s.first()
    fun b() = listOf("abc", "xyz").map(::rotate)
    println(b())


    fun some(a: Int) = a > 10

    fun foo(x: Int, p: (Int) -> Boolean) = if (p(x)) println("maior") else println("menor")

    fun blah(a: Int) = foo(a, { x -> x > 10 })

    fun bleh(a: Int): (Int) -> Boolean = { x -> x == a }

    fun poff(a: Int) = foo(a, ::some)

}
