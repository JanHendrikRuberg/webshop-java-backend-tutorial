package de.oncoding.webshop.repository

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository: JpaRepository<CustomerEntity, String>

@Entity
@Table(name="customers")
class CustomerEntity(
    @Id val id: String,
    val firstName: String,
    val lastName: String,
    val salutation: String,
    val email: String
)