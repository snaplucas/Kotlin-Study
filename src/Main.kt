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
}
