object Classes {

    fun main(args: Array<String>) {
        val a  = Address("nome", "cidade")
        println(a.name)
        println(a.city)
        a.printName()
    }

    class Address(var name: String, var city: String) {
        fun printName() = println(name)
    }
}