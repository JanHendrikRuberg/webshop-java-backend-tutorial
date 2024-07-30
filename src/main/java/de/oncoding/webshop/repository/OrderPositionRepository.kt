package de.oncoding.webshop.repository

import de.oncoding.webshop.model.OrderPositionResponse

class OrderPositionRepository {

    val orderPositions = mutableListOf<OrderPositionResponse>()

    fun save(orderPositionResponse: OrderPositionResponse) {
        orderPositions.add(orderPositionResponse)
    }

}
