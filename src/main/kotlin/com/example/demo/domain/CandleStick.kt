package com.example.demo.domain

import com.example.demo.repository.CandleStickRepositoryCustomImpl
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document

enum class CandleType {
    DAY, ONE_HOUR;
}

@Document(collection = CandleStickRepositoryCustomImpl.collectionName)
@CompoundIndexes(
    CompoundIndex(def = "{'type':1, 'ticker':1, 'timestamp':-1}", name = "candle_index", unique = true)
)
data class CandleStick(
    @Id var id: String? = null,
    val type: CandleType,
    val ticker: String,
    val timestamp: String,
    var high: Int,
    var low: Int
)

fun CandleStick.toDto(): CandleStickDto = CandleStickDto(
    type = type,
    ticker = ticker,
    timestamp = timestamp,
    low = low,
    high = high
)

data class CandleStickDto(
    val type: CandleType,
    val ticker: String,
    val timestamp: String,
    var high: Int,
    var low: Int
)