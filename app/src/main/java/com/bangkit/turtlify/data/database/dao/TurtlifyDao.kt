package com.bangkit.turtlify.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.turtlify.data.database.entity.Turtle

@Dao
interface TurtlifyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTurtles(turtles: List<Turtle>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTurtle(turtle: Turtle)

    @Query("SELECT * FROM Turtle")
    suspend fun getAllTurtles(): List<Turtle>

    @Query("SELECT * FROM Turtle WHERE id = :turtleId")
    suspend fun getTurtleById(turtleId: Int): Turtle
}