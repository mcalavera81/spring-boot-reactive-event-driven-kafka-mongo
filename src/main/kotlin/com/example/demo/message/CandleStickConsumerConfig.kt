package com.example.demo.message

import com.example.demo.domain.StockTickDto
import com.example.demo.service.CandleStickService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import reactor.core.publisher.Flux
import java.util.function.Consumer

@Configuration
class CandleStickConsumerConfig constructor(
    private val service: CandleStickService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun candleStickConsumer(): Consumer<Flux<Message<StockTickDto>>> = Consumer { stream ->
        stream.concatMap { msg ->
                service.processTick(msg.payload)
        }.onErrorContinue { e, _ ->
            logger.error(e.message, e)
        }.subscribe()
    }
}
