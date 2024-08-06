package de.oncoding.webshop.repository

import de.oncoding.webshop.model.OrderResponse
import de.oncoding.webshop.model.OrderStatus
import org.springframework.stereotype.Service

@Service
class OrderRepository {

    private val orders = mutableListOf<OrderResponse>()

    fun save(orderResponse: OrderResponse): OrderResponse {

        orders.add(orderResponse)
        return orderResponse
    }

    fun findById(orderId: String): OrderResponse? {
        return orders.find { it.id == orderId }
    }

    fun findAllByCustomerIdWhereOrderStatusIsNew(customerId: String): List<OrderResponse> {
        return orders.filter{ it.customerId == customerId && it.status == OrderStatus.NEW }
    }
}
