fun main(args: Array<String>) {
    val recursion = Recursion()

    println(recursion.balance("(if (zero? x) max (/ 1 x))".toList()).invoke())

    println(recursion.factorialTail(4).invoke())
    println(recursion.pascal(2, 2))
}