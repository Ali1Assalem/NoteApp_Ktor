package com.example.noteapp_ktor.data.remote.models

data class RemoteNote(
    val noteTitle:String?,
    val description:String?,
    val date:Long,
    val id:String
)
