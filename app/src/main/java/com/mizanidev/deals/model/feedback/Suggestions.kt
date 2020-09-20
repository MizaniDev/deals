package com.mizanidev.deals.model.feedback

data class Suggestions(
    val idUsuario: String,
    val email: String,
    val message: String,
    val dateTime: Long
)