package com.example.demo.initializer

import com.example.demo.domain.Company
import com.example.demo.repository.CandleStickRepository
import com.example.demo.repository.CompanyRepository
import com.example.demo.repository.StockTickRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.runBlocking
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class MasterDataLoader(
    val companyRepository: CompanyRepository,
    val stockTickRepository: StockTickRepository,
    val candleStickRepository: CandleStickRepository
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        runBlocking {
            companyRepository.deleteAll()
            candleStickRepository.deleteAll().awaitFirstOrNull()
            stockTickRepository.deleteAll().awaitFirstOrNull()
            companyRepository.save(Company(name = "Amazon", ticker = "AMZN"))
            companyRepository.save(Company(name = "Apple", ticker = "AAPL"))
            companyRepository.save(Company(name = "Alphabet", ticker = "GOOG"))
            companyRepository.save(Company(name = "Meta", ticker = "META"))
        }
    }
}
