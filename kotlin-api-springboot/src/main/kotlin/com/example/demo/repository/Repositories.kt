package com.example.demo.repository

import com.example.demo.entity.Customer
import org.springframework.data.repository.CrudRepository


interface CustomerRepository : CrudRepository<Customer, Long> {
    fun findByLogin(login: String): Customer?
}