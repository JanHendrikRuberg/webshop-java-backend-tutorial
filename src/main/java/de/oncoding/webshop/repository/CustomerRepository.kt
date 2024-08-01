package de.oncoding.webshop.repository

import de.oncoding.webshop.exceptions.IdNotFoundException
import de.oncoding.webshop.model.CustomerResponse
import org.springframework.stereotype.Service

@Service
class CustomerRepository {

    val customers = listOf(
        CustomerResponse(
            "1",
            "Total",
            "Surprise",
            "total.surprise@gmail.com"
        )
    )

    fun findById(id: String): CustomerResponse {
        return customers.find { it.id == id } ?:
            throw IdNotFoundException("Customer with id $id not found")
    }
}