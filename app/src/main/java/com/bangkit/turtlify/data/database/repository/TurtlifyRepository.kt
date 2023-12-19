package com.bangkit.turtlify.data.database.repository

import com.bangkit.turtlify.data.database.dao.TurtlifyDao
import com.bangkit.turtlify.data.database.entity.Turtle
import com.bangkit.turtlify.data.network.model.ImageUploadResponse


class TurtlifyRepository(private val turtlifyDao: TurtlifyDao) {

    suspend fun insertAllTurtles(turtles: List<Turtle>) {
        turtlifyDao.insertAllTurtles(turtles)
    }

    suspend fun insertTurtle(
        turtle: Turtle,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            turtlifyDao.insertTurtle(turtle)
            onSuccess("Turtle saved")
        } catch (e: Exception) {
            onError(e.message ?: "Unknown error occurred")
        }

    }

    suspend fun getAllTurtles(
        onSuccess: (List<Turtle>) -> Unit,
        onError: (String) -> Unit
    ){
        try {
            val response = turtlifyDao.getAllTurtles()
            onSuccess(response)
        } catch (e: Exception) {
            onError(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun getTurtleById(turtleId: Int): Turtle {
        return turtlifyDao.getTurtleById(turtleId)
    }
}
