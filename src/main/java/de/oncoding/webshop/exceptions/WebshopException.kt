package de.oncoding.webshop.exceptions

import org.springframework.http.HttpStatus


class WebshopException (
    override val message:String,
    val statusCode: HttpStatus
): RuntimeException(message)

class IdNotFoundException (
    override val message:String,
    val statusCode: HttpStatus
): RuntimeException(message)