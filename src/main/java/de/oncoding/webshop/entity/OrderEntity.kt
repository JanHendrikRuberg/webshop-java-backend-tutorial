package de.oncoding.webshop.entity

import de.oncoding.webshop.model.OrderStatus
import de.oncoding.webshop.repository.OrderPositionEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id val id: String,
    val customerId: String,
    val orderTime: LocalDateTime,

    @Enumerated(EnumType.STRING)
    val status: OrderStatus,

    @ElementCollection
    @CollectionTable(name = "order_positions", joinColumns = [JoinColumn(name = "orderId", referencedColumnName = "id")])
    val orderPositions: List<OrderPositionEntity>
)