package com.example.demo.service

import com.example.demo.domain.ApiStockTick
import com.example.demo.domain.ApiStockTickBatch
import com.example.demo.domain.Company
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Random
import javax.annotation.PostConstruct

@Service
class ApiStockService(private val companyService: CompanyService) {

    private lateinit var companies: List<Company>
    private lateinit var pricesSequence: Map<String, Iterator<Int>>

    @PostConstruct
    fun init() {
        companies = runBlocking {
            companyService.findAll().toList(mutableListOf())
        }

        pricesSequence = companies.map {
            when (it.ticker) {
                "AMZN" -> "AMZN" to generateSequence { 30 }.iterator()
                "META" -> "META" to generateSequence { random.nextInt(IntRange(100, 200)) }.iterator()
                "AAPL" -> "AAPL" to generateSequence(0) { it + 1 }.iterator()
                "GOOG" -> "GOOG" to generateSequence(10) { if (it == 20) 10 else 20 }.iterator()
                else -> it.ticker to generateSequence { 0 }.iterator()
            }
        }.toMap()
    }

    suspend fun fetchStocksPrice(): ApiStockTickBatch {
        return ApiStockTickBatch(
            currentTime.next(),
            companies.map { ApiStockTick(it.ticker, pricesSequence.getValue(it.ticker).next()) }
        )
    }

    fun Random.nextInt(range: IntRange): Int {
        return range.first + nextInt(range.last - range.first)
    }

    companion object {
        private val random = Random()
        private val currentTime = generateSequence(LocalDateTime.now()) { it.plusMinutes(random.nextInt(10, 20).toLong()) }.iterator()
    }
}
