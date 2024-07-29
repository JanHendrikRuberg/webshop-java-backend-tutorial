package de.oncoding.webshop.repository

import de.oncoding.webshop.model.CustomerResponse

class CustomerRepository {

    val customers = listOf(
        CustomerResponse(
            "1",
            "Total",
            "Surprise",
            "total.surprise@gmail.com"
        )
    )

    fun findById(id: String): CustomerResponse? {
        return customers.find { it.id == id }
    }
}