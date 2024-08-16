package de.oncoding.webshop.repository

import de.oncoding.webshop.entity.OrderEntity
import de.oncoding.webshop.model.OrderStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDateTime
import java.util.*

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class OrderRepositoryTest {

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setupTestData() {
        customerRepository.save(CustomerEntity(
            id = "234",
            firstName = "",
            lastName = "",
            salutation = "",
            email = ""
        ))
        customerRepository.save(CustomerEntity(
            id = "other-customer-id",
            firstName = "",
            lastName = "",
            salutation = "",
            email = ""
        ))
    }

    @Test
    fun `find all on empty db - returns empty list`() {

        // when
        val result = orderRepository.findAll()

        // then
        assertThat(result).isEmpty()
    }

    @Test
    fun `find all on db after saving order - returns saved order`() {

        // given
        val id1 = UUID.randomUUID().toString()
        val orderEntity = createOrderEntity(id1,"234", OrderStatus.NEW)
        orderRepository.save(orderEntity)

        // when
        val result: OrderEntity = orderRepository.findAll().first()

        // then
        assertThat(result.id).isEqualTo(orderEntity.id)
    }

    @Test
    fun `findAllByCustomerIdWhereOrderStatusIsNew - returns expected order`() {
        // given

        val id1 = UUID.randomUUID().toString()
        orderRepository.save(createOrderEntity(id1, "234", OrderStatus.NEW))
        val id2 = UUID.randomUUID().toString()
        orderRepository.save(createOrderEntity(id2, "other-customer-id", OrderStatus.NEW))

        // when
        val result = orderRepository.findAllByCustomerIdWhereOrderStatusIsNew("234")


        //then
        assertThat(result.size).isEqualTo(1)
        val firstResult = result.first()
        assertThat(firstResult.id).isEqualTo(id1)
    }


    private fun createOrderEntity(id: String, customerId: String, status: OrderStatus) = OrderEntity(
        id = id,
        customerId = customerId,
        orderTime = LocalDateTime.now(),
        status = status,
        orderPositions = emptyList()
    )

}