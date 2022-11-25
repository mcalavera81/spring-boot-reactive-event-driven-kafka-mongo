package com.example.demo.message

import com.example.demo.domain.StockTickDto
import com.example.demo.domain.toDto
import com.example.demo.service.ApiStockService
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.function.Supplier

@Configuration
class StockTickSupplierConfig constructor(
    private val api: ApiStockService
) {

    @Bean
    fun stockTickSupplier(@Value("\${stock-update.frequency.seconds}") stockUpdateFrequencySeconds: Long): Supplier<Flux<StockTickDto>> {
        return Supplier {
            Flux.interval(Duration.ofSeconds(stockUpdateFrequencySeconds)).concatMap {
                mono {
                    val batchPrices = api.fetchStocksPrice()
                    batchPrices.prices.map { it.toDto(batchPrices.timestamp) }
                }
            }.flatMap {
                Flux.fromIterable(it)
            }
        }
    }
}
