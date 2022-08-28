package com.example.noteapp_ktor.data.remote.models

data class User(
    val name:String? = null,
    val email:String,
    val password:String
)
