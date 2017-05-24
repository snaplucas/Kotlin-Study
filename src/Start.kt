fun main(args: Array<String>) {
    val recursion = Recursion()

    println(recursion.balance("(if (zero? x) max (/ 1 x))".toList()))

    println("factorial recursive: " + recursion.factorial(4))
    println("factorial tail recursive: " + recursion.factorialTail(4))

    println(recursion.pascal(2, 2))
    val lista = "abcd".toList()
    val a = lista.map { x -> x + "x" }

    println(a)

    val functions = Functions()

    functions.helloNtimes(10)

    functions.blah(50)

    fun isOdd(x: Int) = x % 2 != 0

    val numbers = listOf(1, 2, 3)
    println(numbers.filter(::isOdd))

    fun rotate(s: String) = s.drop(1) + s.first()
    fun b() = listOf("abc", "xyz").map(::rotate)
    println(b())


    data class Person(val name: String, val age: Int?)

    val person: Person? = Person("Jack", 1)
    person?.age?.let {
        println("The person is aged $it")
    }

    val person2: Person? = Person("Jack", null)
    person2?.age?.let {
        println("The person is aged $it")
    }
}
