package com.example.demo.service

import com.example.demo.domain.StockTick
import com.example.demo.domain.StockTickDto
import com.example.demo.repository.StockTickRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class StockTickService constructor(
    private val repository: StockTickRepository
) {

    fun getChangeStream(): Flux<StockTick> {
        return repository.getChangeStream()
    }

    fun findAll(): Flux<StockTick> {
        return repository.findAll()
    }

    fun deleteAll(): Mono<Void> {
        return repository.deleteAll()
    }

    fun storeTick(stockTickDto: StockTickDto): Mono<StockTick> {
        val stockTick = mapToStockTick(stockTickDto)
        return repository.save(stockTick)
    }

    private fun mapToStockTick(stockTickDto: StockTickDto) = StockTick(
        ticker = stockTickDto.ticker,
        price = stockTickDto.price,
        timestamp = stockTickDto.timestamp
    )
}
