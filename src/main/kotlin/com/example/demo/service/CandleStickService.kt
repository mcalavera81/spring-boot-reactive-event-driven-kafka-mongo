package com.example.demo.service

import com.example.demo.domain.CandleStick
import com.example.demo.domain.CandleType
import com.example.demo.domain.StockTickDto
import com.example.demo.repository.CandleStickRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.format.DateTimeFormatter

@Service
class CandleStickService constructor(
    private val repository: CandleStickRepository

) {

    fun getChangeStream(): Flux<CandleStick> {
        return repository.getChangeStream()
    }

    fun find(ticker: String?, candleType: CandleType?): Flux<CandleStick> =
        when{
            ticker == null && candleType == null -> repository.findAll()
            ticker == null && candleType != null -> repository.findByType(candleType)
            ticker != null && candleType == null -> repository.findByTicker(ticker)
            else -> repository.findByTypeAndTicker(candleType!!, ticker!!)
        }

    fun findAll(): Flux<CandleStick> {
        return repository.findAll()
    }

    fun deleteAll(): Mono<Void> {
        return repository.deleteAll()
    }

    suspend fun updateCandle(candleType: CandleType, stockTickDto: StockTickDto) {
        val ticker = stockTickDto.ticker
        val price = stockTickDto.price
        val formattedTimestamp = stockTickDto.timestamp.format(formatters.getValue(candleType))
        val candle = repository.findByTypeAndTickerAndTimestamp(candleType, ticker, formattedTimestamp)
            .awaitFirstOrNull()

        candle?.also {
            val high = if (price > candle.high) price else candle.high
            val low = if (price < candle.low) price else candle.low
            val updatedCandle = candle.copy(high = high, low = low)
            if (updatedCandle != candle) {
                repository.save(updatedCandle).awaitFirst()
            }
        } ?: run {
            val newCandle = CandleStick(
                type = candleType,
                ticker = ticker,
                low = price,
                high = price,
                timestamp = formattedTimestamp
            )
            repository.save(newCandle).awaitFirst()
        }
    }

    fun processTick(stockTickDto: StockTickDto): Mono<Void> {
        return mono {
            updateCandle(CandleType.ONE_HOUR, stockTickDto)
            updateCandle(CandleType.DAY, stockTickDto)
            null
        }
    }

    companion object {
        private val oneHourFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00:00")
        private val dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00")
        private val formatters = mapOf(
            CandleType.ONE_HOUR to oneHourFormatter,
            CandleType.DAY to dayFormatter
        )
    }
}
