package com.example.demo.repository

import com.example.demo.entity.Article
import com.example.demo.entity.User
import org.springframework.data.repository.CrudRepository

interface ArticleRepository : CrudRepository<Article, Long> {
    fun findByTitle(title: String): Article?
    fun findAllByOrderByAddedAtDesc(): Iterable<Article>
}

interface UserRepository : CrudRepository<User, Long> {
    fun findByLogin(login: String): User?
}