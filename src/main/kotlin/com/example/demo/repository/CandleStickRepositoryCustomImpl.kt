package com.example.demo.repository

import com.example.demo.domain.CandleStick
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import reactor.core.publisher.Flux

class CandleStickRepositoryCustomImpl(private val template: ReactiveMongoTemplate) : CandleStickRepositoryCustom {

    override fun getChangeStream(): Flux<CandleStick> {
        return template.changeStream(CandleStick::class.java).watchCollection(collectionName).listen().map { it.body }
    }

    companion object {
        const val collectionName = "candleStick"
    }
}
