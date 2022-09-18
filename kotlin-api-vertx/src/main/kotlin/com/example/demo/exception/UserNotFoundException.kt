package com.example.demo.exception

class UserNotFoundException(val login: String) : Exception() {

}