package com.example.demo.repository

import com.example.demo.entity.UserEntity
import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.*
import io.micronaut.data.model.*
import io.micronaut.data.repository.CrudRepository

@Repository
interface UserRepository : CrudRepository<UserEntity, Long> {
    @Executable
    public fun findByLogin(login: String): UserEntity
}