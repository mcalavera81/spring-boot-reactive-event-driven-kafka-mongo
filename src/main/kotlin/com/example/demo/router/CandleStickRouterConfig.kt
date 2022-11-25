package com.example.demo.router

import com.example.demo.handler.CandleStickHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class CandleStickRouterConfig {

    @Bean("stockCandleRoutes")
    fun router(candleStickHandler: CandleStickHandler) = router {
        "/api/v1/candles".nest {
            GET("/changelog", candleStickHandler::getChangeStream)
            GET("", candleStickHandler::find)
            POST("/delete-all", candleStickHandler::deleteAll)
        }
    }
}
