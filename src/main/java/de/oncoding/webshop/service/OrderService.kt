package de.oncoding.webshop.service

import de.oncoding.webshop.model.*
import de.oncoding.webshop.repository.CustomerRepository
import de.oncoding.webshop.repository.OrderPositionRepository
import de.oncoding.webshop.repository.OrderRepository
import de.oncoding.webshop.repository.ProductRepository
import java.util.UUID

class OrderService {

    val orderRepository = OrderRepository()
    val orderPositionRepository = OrderPositionRepository()
    val customerRepository = CustomerRepository()
    val productRepository = ProductRepository()

    fun createOrder(request: OrderCreateRequest): OrderResponse {

        val customer: CustomerResponse = customerRepository.findById(request.customerId)
            ?: throw Exception("Customer not found")

        return orderRepository.save(request)
    }

    fun createNewPositionForOrder(
        orderId: String,
        request: OrderPositionCreateRequest
    ): OrderPositionResponse {

        orderRepository.findById(orderId) ?:
            throw Exception("Order not found")

        if(productRepository.findById(request.productId).isEmpty())
            throw Exception("Product not found")

        val orderPositionResponse = OrderPositionResponse(
            id = UUID.randomUUID().toString(),
            productId = request.productId,
            quantity = request.quantity
        )
        orderPositionRepository.save(orderPositionResponse)
        return orderPositionResponse
    }


}