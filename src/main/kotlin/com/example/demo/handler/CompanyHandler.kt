package com.example.demo.handler

import com.example.demo.domain.toDto
import com.example.demo.repository.CompanyRepository
import kotlinx.coroutines.flow.map
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyAndAwait

@Component
class CompanyHandler(private val companyRepository: CompanyRepository) {

    suspend fun all(req: ServerRequest): ServerResponse {
        return ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait(
            companyRepository.findAll().map { it.toDto() }
        )
    }
}
