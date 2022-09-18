package com.example.demo.exception

class SaveUserException() : Exception(){

    lateinit var login: String

    constructor(login: String) : this(){
        this.login = login;
    }

}
