package de.oncoding.webshop.repository

import de.oncoding.webshop.model.OrderPositionResponse
import org.springframework.stereotype.Service

@Service
class OrderPositionRepository {

    val orderPositions = mutableListOf<OrderPositionResponse>()

    fun save(orderPositionResponse: OrderPositionResponse) {
        orderPositions.add(orderPositionResponse)
    }

}
