package com.example.noteapp_ktor.utils

sealed class Result<T>(val data:T? = null, val errorMessage:String?= null){

    class Success<T>(data:T,errorMessage: String? =null):Result<T>(data,errorMessage)
    class Error<T>(errorMessage: String,data: T? = null):Result<T>(data,errorMessage)
    class Loading<T>:Result<T>()

}
