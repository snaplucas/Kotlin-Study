package fundamentals

class Functions {

    fun some(a: Int) = a > 10

    fun foo(x: Int, p: (Int) -> Boolean) = if (p(x)) println("maior") else println("menor")

    fun blah(a: Int) = foo(a, { x -> x > 10 })

    fun bleh(a: Int): (Int) -> Boolean = { x -> x == a }

    fun poff(a: Int) = foo(a, { some(it) })

    fun helloNtimes(n: Int) = n.times { println("Hello") }

    fun Int.times(f: () -> Unit) {
        for (i in 1..this) {
            f()
        }
    }

    fun transform(color: String): Int {
        return when (color) {
            "Red" -> 0
            "Green" -> 1
            "Blue" -> 2
            else -> throw IllegalArgumentException("Invalid color param value")
        }
    }

    fun String.removeFirstLastChar() =  this.substring(1, this.length - 1)


}