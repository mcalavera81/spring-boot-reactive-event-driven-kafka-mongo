package com.example.demo.repository

import com.example.demo.domain.StockTick
import reactor.core.publisher.Flux

interface StockTickRepositoryCustom {
    fun getChangeStream(): Flux<StockTick>
}
