package com.bangkit.turtlify.data.database.repository


data class Turtle(
    val name: String,
    val latinName: String,
    val status: String,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double
)
class DatabaseRepository {
    fun getHistory(){
//        val turtleHistories = listOf<Turtle>(Turtle())
    }
}