package com.example.demo.router

import com.example.demo.handler.CompanyHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class CompanyRouterConfig {

    @Bean("companyRoutes")
    fun routes(companyHandler: CompanyHandler) = coRouter {
        "/api/v1".nest {
            GET("/companies", companyHandler::all)
        }
    }
}
