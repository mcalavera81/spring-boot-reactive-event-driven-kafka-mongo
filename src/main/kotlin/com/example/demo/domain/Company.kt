package com.example.demo.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Company(@Id val _id: String? = null, val name: String, @Indexed(unique = true) val ticker: String)

data class CompanyDto(
    val name: String,
    val ticker: String
)

fun Company.toDto(): CompanyDto = CompanyDto(
    name = name,
    ticker = ticker
)
