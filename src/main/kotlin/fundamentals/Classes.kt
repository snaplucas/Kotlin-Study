package fundamentals

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
}

data class Band(val guitar: String, val bass: String, val drums: String)

class Panel(val Id: Int, val name: String)

fun main(args: Array<String>) {
    val a = fundamentals.Address("nome", "cidade")
    println(a.name)
    println(a.city)
    a.street = "rua"
    println(a.street)
    a.printName()

    //destructuring
    val band = fundamentals.Band(guitar = "Alex", bass = "Geddy", drums = "Neil")
    val (guitar, bass, drums) = band

    println(band)
    println(guitar)
    println(bass)
    println(drums)

    val panel = Panel(1, "teste")
    println(panel.Id)
    println(panel.name)
}