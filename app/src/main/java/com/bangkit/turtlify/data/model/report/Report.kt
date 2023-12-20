package com.bangkit.turtlify.data.model.report

data class Report(
    val userEmail: String,
    val userMessage: String
)

data class TurtleLocation(
    var lat: String,
    var long: String
)
data class FormData(
    var reporterName: String,
    var reporterContact: String,
    var turtleLocation: TurtleLocation,
    var contact: String,
    var turtleName: String
)

data class SimpleTurtle(
    val name: String,
    val image: String
)