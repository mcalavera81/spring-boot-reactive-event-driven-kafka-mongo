package com.example.demo.repository

import com.example.demo.domain.Company
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CompanyRepository : CoroutineCrudRepository<Company, String>