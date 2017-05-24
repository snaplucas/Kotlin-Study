object Classes {

    fun main(args: Array<String>) {
        val a = Address("nome", "cidade")
        println(a.name)
        println(a.city)
        a.street = "rua"
        println(a.street)
        a.printName()
    }

    class Address(var name: String, var city: String) {
        fun printName() = println(name)

        var country: String? = ""

        val isBrasil get() = this.country == "Brasil"

        var counter = 0
            set(value) {
                if (value >= 0) field = value
            }

        var street: String = ""
            get() = this.toString()
            set(value) {
                field = value
            }
    }
}