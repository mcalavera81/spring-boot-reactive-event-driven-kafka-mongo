package com.example.demo.handler

import com.example.demo.domain.CandleStick
import com.example.demo.domain.CandleType
import com.example.demo.domain.toDto
import com.example.demo.service.CandleStickService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.sse
import reactor.core.publisher.Mono

@Component
class CandleStickHandler(
    private val candleStickService: CandleStickService
) {

    fun getChangeStream(req: ServerRequest): Mono<ServerResponse> {
        return ok().sse().body(
            candleStickService.getChangeStream().map { it.toDto() },
            CandleStick::class.java
        )
    }

    fun find(req: ServerRequest): Mono<ServerResponse> {
        return try {
            val ticker: String? = req.queryParam("ticker").map { it.uppercase() }.orElse(null)
            val unit: CandleType? = req.queryParam("type").map { CandleType.valueOf(it.uppercase()) }.orElse(null)

            ok().contentType(MediaType.APPLICATION_JSON).body(
                candleStickService.find(ticker, unit),
                CandleStick::class.java
            )
        } catch (ex: Exception) {
            ServerResponse.badRequest().bodyValue(mapOf("error" to ex.localizedMessage))
        }
    }

    fun deleteAll(req: ServerRequest): Mono<ServerResponse> {
        return ok().build(candleStickService.deleteAll())
    }
}
