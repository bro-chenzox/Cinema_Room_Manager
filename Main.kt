package cinema

fun main() {
    val rows = printInstructionAndReadInt("Enter the number of rows:")
    val seats = printInstructionAndReadInt("Enter the number of seats in each row:")
    val cinema = Cinema(rows, seats)
    println()
    
    showMenu(cinema.menu)
    var item = readInt()
    println()
    
    while (item in 1..3) {
        process(item, cinema)
        showMenu(cinema.menu)
        println()
        item = readInt()
    } 
}

private fun process(item: Int, cinema: Cinema) {
    when (item) {
        1 -> cinema.showTheSeats()
        2 -> cinema.buyTicket()
        3 -> cinema.showStatistics()
    }
    println()
}

private fun showMenu(options: List<String>) = options.forEach { println(it) }

private fun printInstructionAndReadInt(text: String): Int {
  	println(text)
  	return readInt()
}

private fun readInt() = readLine()?.toInt() ?: 0

class Cinema(val rows: Int, val seats: Int) {
    
    private val totalSeats = rows * seats
    private val ticketPrice = 10
    private val cheapTicketPrice = 8
    private var purchasedTickets = 0
    private var currentIncome = 0
    private val seats2D: MutableList<MutableList<Char>> = MutableList(rows) { MutableList<Char>(seats) {'S'}}
    val menu = listOf(
        "1. Show the seats",
        "2. Buy a ticket",
        "3. Statistics",
        "0. Exit")
    
    fun showTheSeats() {
        var sequence = "Cinema:\n  ${(1..seats).joinToString(" ")}"
        
        for (i in 1..rows) {
            var string = ""
            for (j in 1..seats) {
                string = "$string ${seats2D[i-1][j-1]}"
            }
            sequence = "$sequence\n$i$string"
        }
        println(sequence)
  	}
  
  	fun buyTicket() {
      	val row = printInstructionAndReadInt("Enter a row number:")
      	val seat = printInstructionAndReadInt("Enter a seat number in that row:")
        if (row !in 1..rows || seat !in 1..seats) { 
            println("Wrong input!")
            buyTicket()
        }
        else if (seats2D[row-1][seat-1] == 'B') {
            println("That ticket has already been purchased!")
            buyTicket()
        } else {
            seats2D[row-1][seat-1] = 'B'
            purchasedTickets++
        }
      	showTheSeats()
      
      	val seatPrice = getTicketPrice(row)
        currentIncome = currentIncome + seatPrice
      	println("Ticket price: \$$seatPrice")
  	}
  
  	fun getTicketPrice(row: Int): Int {
      	return if (totalSeats > 60) {
          	if (row > (rows / 2)) cheapTicketPrice
          	else ticketPrice   
      	} else ticketPrice
  	}
  
  	fun showStatistics() {
        val percentage = if (purchasedTickets > 0) purchasedTickets * 100.0 / totalSeats else 0.00
      
      	var totalIncome = 0
      	seats2D.forEachIndexed { index, _ -> totalIncome = totalIncome + getTicketPrice(index+1) * seats }
      	
      	println("Number of purchased tickets: $purchasedTickets\n" +
                "Percentage: ${"%.2f".format(percentage)}%\n" +
                "Current income: \$$currentIncome\n" +
                "Total income: \$$totalIncome")    
    }
}
