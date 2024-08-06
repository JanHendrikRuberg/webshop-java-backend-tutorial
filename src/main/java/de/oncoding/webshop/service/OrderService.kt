package de.oncoding.webshop.service

import de.oncoding.webshop.exceptions.IdNotFoundException
import de.oncoding.webshop.model.*
import de.oncoding.webshop.repository.CustomerRepository
import de.oncoding.webshop.repository.OrderPositionRepository
import de.oncoding.webshop.repository.OrderRepository
import de.oncoding.webshop.repository.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class OrderService (
    val productRepository: ProductRepository,
    val orderRepository: OrderRepository,
    val orderPositionRepository: OrderPositionRepository,
    val customerRepository: CustomerRepository
){

    fun createOrder(request: OrderCreateRequest): OrderResponse {
        customerRepository.findById(request.customerId)

        val orderResponse = OrderResponse(
            id = UUID.randomUUID().toString(),
            customerId = request.customerId,
            orderTime = LocalDateTime.now(),
            status = OrderStatus.NEW,
            orderPositions = emptyList()
        )

        return orderRepository.save(orderResponse)
    }

    fun createNewPositionForOrder(
        orderId: String,
        request: OrderPositionCreateRequest
    ): OrderPositionResponse {

        orderRepository.findById(orderId) ?:
            throw IdNotFoundException(
                message = "Order with $orderId not found",
                statusCode = HttpStatus.BAD_REQUEST)

        if(productRepository.findById(request.productId).isEmpty())
            throw IdNotFoundException(
                message = "Product with ${request.productId} not found",
                statusCode = HttpStatus.BAD_REQUEST)

        val orderPositionResponse = OrderPositionResponse(
            id = UUID.randomUUID().toString(),
            orderId = orderId,
            productId = request.productId,
            quantity = request.quantity
        )
        orderPositionRepository.save(orderPositionResponse)
        return orderPositionResponse
    }

    fun updateOrder(id: String, request: OrderUpdateRequest): OrderResponse {
        val order = orderRepository.findById(id)
            ?: throw IdNotFoundException("Order with id $id not found")

        val updatedOrder = order.copy(
            status = request.orderStatus ?: order.status
        )

        return orderRepository.save(updatedOrder)
    }
}