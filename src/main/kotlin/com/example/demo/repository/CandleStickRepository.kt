package com.example.demo.repository

import com.example.demo.domain.CandleStick
import com.example.demo.domain.CandleType
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CandleStickRepository : ReactiveCrudRepository<CandleStick, String>, CandleStickRepositoryCustom {

    fun findByTypeAndTickerAndTimestamp(type: CandleType, ticker: String, timestamp: String): Mono<CandleStick>

    fun findByTypeAndTicker(type: CandleType, ticker: String): Flux<CandleStick>

    fun findByType(type: CandleType): Flux<CandleStick>

    fun findByTicker(ticker: String): Flux<CandleStick>
}
