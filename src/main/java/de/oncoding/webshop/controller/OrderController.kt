package de.oncoding.webshop.controller

import de.oncoding.webshop.model.OrderCreateRequest
import de.oncoding.webshop.model.OrderPositionCreateRequest
import de.oncoding.webshop.model.OrderResponse
import de.oncoding.webshop.service.OrderService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    val orderService: OrderService
) {

    @PostMapping("/orders")
    fun createOrder(
        @RequestBody request: OrderCreateRequest
    ): OrderResponse{
        return orderService.createOrder(request)
    }

    @PostMapping("/orders/{id}/positions")
    fun createOrderPosition(
        @PathVariable(name = "id") orderId: String,
        @RequestBody request: OrderPositionCreateRequest
    ) {
        orderService.createNewPositionForOrder(orderId, request)
    }

}