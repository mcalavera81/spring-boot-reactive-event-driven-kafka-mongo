package com.example.demo.handler

import com.example.demo.domain.StockTick
import com.example.demo.domain.toDto
import com.example.demo.service.StockTickService
import kotlinx.coroutines.runBlocking
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.sse
import reactor.core.publisher.Mono

@Component
class StockTickHandler(
    private val stockTickService: StockTickService
) {

    fun getChangeStream(req: ServerRequest): Mono<ServerResponse> {
        return ok().sse().body(
            stockTickService.getChangeStream().map { it.toDto() },
            StockTick::class.java
        )
    }

    fun findAll(req: ServerRequest): Mono<ServerResponse> {
        return ok().contentType(MediaType.APPLICATION_JSON).body(
            stockTickService.findAll(),
            StockTick::class.java
        )
    }

    fun deleteAll(req: ServerRequest): Mono<ServerResponse> {
        return ok().build(stockTickService.deleteAll())
    }
}
