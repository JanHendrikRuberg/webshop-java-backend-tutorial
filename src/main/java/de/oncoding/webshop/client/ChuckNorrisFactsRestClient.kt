package de.oncoding.webshop.client

import de.oncoding.webshop.model.JokeResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.time.LocalDate
import java.util.UUID

interface ChuckNorrisFactsClient {
    fun getRandomFacts(): JokeResponse
}

// prod -> production, cloud
// sandbox -> sandbox, cloud
// acceptance -> acceptance, cloud
// local ->

@Profile("cloud")
@Service
class ChuckNorrisFactsRestClient(
    @Value("\${chuckNorrisApi.baseUrl}")
    val baseUrl: String
): ChuckNorrisFactsClient {

    val restTemplate = RestTemplate()

    override fun getRandomFacts(): JokeResponse {
        val jokeUrl = "$baseUrl/jokes/random"

        try {
            val entity: ResponseEntity<JokeResponse> = restTemplate.getForEntity(URI(jokeUrl), JokeResponse::class.java)
            return entity.body ?: throw Exception()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}

@Profile("!cloud")
@Service
class ChuckNorrisFactsMockClient(): ChuckNorrisFactsClient {

    override fun getRandomFacts(): JokeResponse {
        return JokeResponse(
            categories = emptyList(),
            createdAt = LocalDate.now(),
            iconUrl = "",
            id = UUID.randomUUID().toString(),
            url = "",
            value = "This is a Chuck Norris fact"
        )
    }
}