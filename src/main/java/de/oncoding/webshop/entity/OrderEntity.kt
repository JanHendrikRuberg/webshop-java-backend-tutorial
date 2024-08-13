package de.oncoding.webshop.entity

import de.oncoding.webshop.model.OrderStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id val id: String,
    val customerId: String,
    val orderTime: LocalDateTime,
    @Enumerated(EnumType.STRING)
    val status: OrderStatus
)