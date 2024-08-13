package de.oncoding.webshop.model

import java.time.LocalDateTime

data class GetOrderResponse(
    val id: String,
    val orderTime: LocalDateTime,
    val status: OrderStatus,
    val customer: CustomerResponse,
    val orderPositions: List<GetOrderPositionResponse>
)

data class GetOrderPositionResponse (
    val id: String,
    val product: ProductResponse,
    val quantity: Long
)
