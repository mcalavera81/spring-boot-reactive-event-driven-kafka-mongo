package com.example.demo.service

import com.example.demo.domain.Company
import com.example.demo.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Component

@Component
class CompanyService(private val repository: CompanyRepository) {

    fun findAll(): Flow<Company> {
        return repository.findAll()
    }
}
