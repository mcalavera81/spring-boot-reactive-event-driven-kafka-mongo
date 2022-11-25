package com.example.demo.repository

import com.example.demo.domain.CandleStick
import reactor.core.publisher.Flux

interface CandleStickRepositoryCustom {
    fun getChangeStream(): Flux<CandleStick>
}
