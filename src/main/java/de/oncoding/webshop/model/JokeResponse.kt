package de.oncoding.webshop.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class JokeResponse(
    val categories: List<String>,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.n")
    @JsonProperty("created_at")
    val createdAt: LocalDate,
    @JsonProperty("icon_url")
    val iconUrl: String,
    val id: String,
    val url: String,
    val value: String
)