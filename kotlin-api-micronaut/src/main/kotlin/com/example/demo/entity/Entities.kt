package com.example.demo.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class UserEntity (
    var login: String,
    var firstname: String,
    var lastname: String,
    var description: String? = null,
    @Id @GeneratedValue
    var id: Long? = null)