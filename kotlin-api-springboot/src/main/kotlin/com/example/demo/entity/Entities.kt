package com.example.demo.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Customer(
    var login: String,
    var firstname: String? = null,
    var lastname: String,
    var description: String? = null,
    @Id @GeneratedValue var id: Long? = null)