package com.example.demo.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Article(
    var title: String,
    var headline: String,
    var content: String,

    @ManyToOne
    var author: User,
    //@ManyToOne var author: Customer,
    var addedAt: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue
    var id: Long? = null)

@Entity
class User (
    var login: String,
    var firstname: String,
    var lastname: String,
    var description: String? = null,
    @Id @GeneratedValue
    var id: Long? = null)