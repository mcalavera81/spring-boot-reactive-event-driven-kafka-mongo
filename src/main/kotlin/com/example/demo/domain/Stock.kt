package com.example.demo.domain

import com.example.demo.repository.CandleStickRepositoryCustomImpl
import com.example.demo.repository.StockTickRepositoryCustomImpl
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ApiStockTickBatch(val timestamp: LocalDateTime, val prices: List<ApiStockTick>)

data class ApiStockTick(val ticker: String, val price: Int)

fun ApiStockTick.toDto(timestamp: LocalDateTime): StockTickDto = StockTickDto(
    ticker = ticker,
    price = price,
    timestamp = timestamp
)



@Document(collection = StockTickRepositoryCustomImpl.collectionName)
data class StockTick(@Id val id: String? = null, val timestamp: LocalDateTime, val ticker: String, val price: Int)
data class StockTickDto(val timestamp: LocalDateTime, val ticker: String, val price: Int)

fun StockTick.toDto(): StockTickDto = StockTickDto(
    ticker = ticker,
    price = price,
    timestamp = timestamp
)

