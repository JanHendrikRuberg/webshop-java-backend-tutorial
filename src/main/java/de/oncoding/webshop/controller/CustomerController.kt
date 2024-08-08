package de.oncoding.webshop.controller

import de.oncoding.webshop.model.CustomerResponse
import de.oncoding.webshop.model.ShoppingCartResponse
import de.oncoding.webshop.repository.CustomerEntity
import de.oncoding.webshop.repository.CustomerRepository
import de.oncoding.webshop.service.ShoppingCartService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(
    val customerRepository: CustomerRepository,
    val shoppingCartService: ShoppingCartService
) {

    @GetMapping("/customers/{id}")
    fun getCustomerById(
        @PathVariable id: String
    ) : CustomerResponse {
        val customer: CustomerEntity = customerRepository.getReferenceById(id)
        return CustomerResponse(
            id = customer.id,
            firstName = customer.firstName,
            lastName = customer.lastName,
            email = customer.email
        )
    }

    @GetMapping("/customers/{id}/shoppingcart")
    fun getShoppingCartByCustomerId(
        @PathVariable id: String
    ): ShoppingCartResponse {
        return shoppingCartService.getShoppingCartForCustomer(id)
    }

}