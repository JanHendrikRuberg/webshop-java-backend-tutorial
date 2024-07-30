package de.oncoding.webshop.model

data class OrderPositionCreateRequest(
    val productId: String,
    val quantity: Long
)
