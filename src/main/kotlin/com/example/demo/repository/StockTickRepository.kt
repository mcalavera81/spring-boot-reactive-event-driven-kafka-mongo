package com.example.demo.repository

import com.example.demo.domain.StockTick
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface StockTickRepository : ReactiveCrudRepository<StockTick, String>, StockTickRepositoryCustom
