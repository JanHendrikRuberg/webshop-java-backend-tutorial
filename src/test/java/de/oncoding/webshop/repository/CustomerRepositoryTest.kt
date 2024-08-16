package de.oncoding.webshop.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@Testcontainers
class CustomerRepositoryTest {

    @Autowired
    lateinit var repository: CustomerRepository

    @Test
    fun `find all on empty db - returns empty List`() {

        // when
        val result: List<CustomerEntity> = repository.findAll()

        // then
        assertThat(result).isEmpty()
    }

    @Test
    fun `find all on db after saving customer - returns list with customer`() {
        // given
        val customerEntity = CustomerEntity(
            id = "123",
            email = "donald@duck.com",
            firstName = "Donald",
            lastName = "Duck",
            salutation = "Mr."
        )
        repository.save(customerEntity)

        // when
        val result: List<CustomerEntity> = repository.findAll()

        // then
        assertThat(result.get(0).id).isEqualTo(customerEntity.id)
    }
}