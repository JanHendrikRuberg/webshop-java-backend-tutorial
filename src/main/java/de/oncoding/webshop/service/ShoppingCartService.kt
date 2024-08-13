package de.oncoding.webshop.service

import de.oncoding.webshop.entity.ProductEntity
import de.oncoding.webshop.exceptions.IdNotFoundException
import de.oncoding.webshop.model.OrderPositionResponse
import de.oncoding.webshop.model.ShoppingCartResponse
import de.oncoding.webshop.entity.OrderEntity
import de.oncoding.webshop.repository.OrderPositionRepository
import de.oncoding.webshop.repository.OrderRepository
import de.oncoding.webshop.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ShoppingCartService(
    val orderRepository: OrderRepository,
    val orderPositionRepository: OrderPositionRepository,
    val productRepository: ProductRepository

) {
    fun getShoppingCartForCustomer(customerId: String): ShoppingCartResponse {

        val orders: List<OrderEntity> = orderRepository.findAllByCustomerIdWhereOrderStatusIsNew(customerId)
        val orderIds = orders.map { it.id }

        val orderPositions = orderPositionRepository.findAllById(orderIds).map { OrderService.mapToResponse(it) }

        val deliveryCost = 800L // TODO: feature to select delivery method?
        val totalAmount = calculateSumForCart(orderPositions, deliveryCost)

        return ShoppingCartResponse(
            customerId = customerId,
            orderPositions = orderPositions,
            deliveryOption = "STANDARD",
            deliveryCostInCent = deliveryCost,
            totalAmountInCent = totalAmount
        )
    }

    fun calculateSumForCart(
        orderPositions: List<OrderPositionResponse>,
        deliveryCost: Long
    ): Long {
        val positionsAmounts: List<Long> = orderPositions.map {
            val product: ProductEntity =
                productRepository
                    .findById(it.productId)
                    .orElseThrow { throw IdNotFoundException("Product with id ${it.productId} not found") }
            if (it.quantity <= 0)
                throw IllegalArgumentException("Orderposition with quantity of ${it.quantity} is not allowed")
            it.quantity * product.priceInCent
        }
        val positionSum = positionsAmounts.sumOf { it.toInt() }
        return positionSum + deliveryCost
    }

}
