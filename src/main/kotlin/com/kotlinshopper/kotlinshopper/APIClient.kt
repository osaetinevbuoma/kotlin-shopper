package com.kotlinshopper.kotlinshopper

import org.springframework.http.MediaType
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec

class APIClient(private val baseUrl: String) {
    private var webClient: WebClient
    private var headers: Map<String, String>

    init {
        headers = mutableMapOf("Content-Type" to "application/json")
        webClient = WebClient.create(this.baseUrl)
    }

    fun get(uri: String, headers: Map<String, String>?): ResponseSpec {
        if (headers != null) {
            this.headers = this.headers + headers
        }

        return webClient.get()
            .uri(uri)
            .headers { httpHeaders -> httpHeaders + this.headers }
            .retrieve()
    }

    fun post(
        uri: String,
        headers: Map<String, String>?,
        data: MultiValueMap<String, String>
    ): ResponseSpec {
        if (headers != null) {
            this.headers = this.headers + headers
        }

        return webClient.post()
            .uri(uri)
            .headers { httpHeaders -> httpHeaders + this.headers }
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromFormData(data))
            .retrieve()
    }

    fun put(
        uri: String,
        headers: Map<String, String>?,
        data: MultiValueMap<String, String>
    ): ResponseSpec {
        if (headers != null) {
            this.headers = this.headers + headers
        }

        return webClient.put()
            .uri(uri)
            .headers { httpHeaders -> httpHeaders + this.headers }
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromFormData(data))
            .retrieve()
    }

    fun delete(uri: String, headers: Map<String, String>?): ResponseSpec {
        if (headers != null) {
            this.headers = this.headers + headers
        }

        return webClient.delete()
            .uri(uri)
            .headers { httpHeaders -> httpHeaders + this.headers }
            .retrieve()
    }
}
