package com.example.demo.repository

import com.example.demo.domain.StockTick
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import reactor.core.publisher.Flux

class StockTickRepositoryCustomImpl(private val template: ReactiveMongoTemplate) : StockTickRepositoryCustom {

    override fun getChangeStream(): Flux<StockTick> {
        return template.changeStream(StockTick::class.java).watchCollection(collectionName).listen().map { it.body }
    }

    companion object {
        const val collectionName = "stockTick"
    }
}
