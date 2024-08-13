package de.oncoding.webshop.service

import de.oncoding.webshop.entity.OrderEntity
import de.oncoding.webshop.exceptions.IdNotFoundException
import de.oncoding.webshop.model.*
import de.oncoding.webshop.repository.*
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

        val order = OrderEntity(
            id = UUID.randomUUID().toString(),
            customerId = request.customerId,
            orderTime = LocalDateTime.now(),
            status = OrderStatus.NEW
        )

        val savedOrder = orderRepository.save(order)
        return mapToResponse(savedOrder)
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

        val orderPosition = OrderPositionEntity(
            id = UUID.randomUUID().toString(),
            orderId = orderId,
            productId = request.productId,
            quantity = request.quantity
        )
        val savedOrderPosition = orderPositionRepository.save(orderPosition)

        return mapToResponse(savedOrderPosition)
    }

    fun updateOrder(id: String, request: OrderUpdateRequest): OrderResponse {
        val order = orderRepository.getReferenceById(id)

        val updatedOrder = order.copy(
            status = request.orderStatus ?: order.status
        )

        val savedOrder = orderRepository.save(updatedOrder)
        return mapToResponse(savedOrder)
    }

    private fun mapToResponse(savedOrder: OrderEntity) = OrderResponse(
        id = savedOrder.id,
        customerId = savedOrder.customerId,
        orderTime = savedOrder.orderTime,
        status = savedOrder.status,
        orderPositions = emptyList()
    )

    fun getOrder(id: String): GetOrderResponse {
        val order = orderRepository.getReferenceById(id)

        val customer = customerRepository.getReferenceById(order.customerId)

        val positions = orderPositionRepository.findAll()
                                .filter { it.orderId == order.id }
                                .map {
                                    val productEntity = productRepository.getReferenceById(it.productId)
                                    GetOrderPositionResponse(
                                        id = it.id,
                                        quantity = it.quantity,
                                        product = ProductResponse(
                                            productEntity.id,
                                            productEntity.name,
                                            productEntity.description,
                                            productEntity.priceInCent,
                                            productEntity.tags
                                        )
                                ) }

        return GetOrderResponse(
            id = order.id,
            status = order.status,
            orderTime = order.orderTime,
            customer = CustomerResponse(
                customer.id,
                customer.firstName,
                customer.lastName,
                customer.email
            ),
            orderPositions = positions
        )
    }

    companion object {
        fun mapToResponse(savedOrderPosition: OrderPositionEntity) =
            OrderPositionResponse(
                id = savedOrderPosition.id,
                orderId = savedOrderPosition.orderId,
                productId = savedOrderPosition.productId,
                quantity = savedOrderPosition.quantity
            )
    }

}