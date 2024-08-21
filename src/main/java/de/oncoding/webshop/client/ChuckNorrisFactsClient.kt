package de.oncoding.webshop.client

import de.oncoding.webshop.model.JokeResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URI

@Service
class ChuckNorrisFactsClient(
    @Value("\${chuckNorrisApi.baseUrl}")
    val baseUrl: String
) {

    val restTemplate = RestTemplate()

    fun getRandomFacts(): JokeResponse {
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