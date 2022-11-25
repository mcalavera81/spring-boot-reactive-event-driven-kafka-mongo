package com.example.demo.router

import com.example.demo.handler.StockTickHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class StockTickRouterConfig {

    @Bean("stockTickRoutes")
    fun router(stockTickHandler: StockTickHandler) = router {
        "/api/v1/ticks".nest {
            GET("/changelog", stockTickHandler::getChangeStream)
            GET("", stockTickHandler::findAll)
            POST("/delete-all", stockTickHandler::deleteAll)
        }
    }
}
