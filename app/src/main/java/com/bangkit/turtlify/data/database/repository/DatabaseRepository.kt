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
    suspend fun getHistory(): List<Turtle>? {
        var histories:List<Turtle>? = null

        try {
            histories = listOf(
                Turtle(
                    "Floating Market Lembang",
                    "Latin name",
                    "dilindungi",
                    "https://www.fisheries.noaa.gov/s3/styles/original/s3/2021-07/640x427-Turtle_Green_NOAAFisheries.png",
                    -6.8168954,
                    107.6151046
                ),
                Turtle(
                    "The Great Asia Africa",
                    "Latin name",
                    "dilindungi",
                    "https://images.squarespace-cdn.com/content/v1/59cae0d6be42d63f64cf6dd2/1542902265247-7TO79BP2LWFFEALYZJK9/Witherington2018-10.png?format=750w",
                    -6.8331128,
                    107.6048483
                ),
                Turtle(
                    "Rabbit Town",
                    "Latin name",
                    "Tidak dilindungi",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRrcsDrhi0zMrpg46TWprj3iNo87nUURujNGQ&usqp=CAU",
                    -6.8668408,
                    107.608081
                ),
                Turtle(
                    "Floating Market Lembang",
                    "Latin name",
                    "dilindungi",
                    "https://www.fisheries.noaa.gov/s3/styles/original/s3/2021-07/640x427-Turtle_Green_NOAAFisheries.png",
                    -6.8168954,
                    107.6151046
                ),
                Turtle(
                    "The Great Asia Africa",
                    "Latin name",
                    "dilindungi",
                    "https://images.squarespace-cdn.com/content/v1/59cae0d6be42d63f64cf6dd2/1542902265247-7TO79BP2LWFFEALYZJK9/Witherington2018-10.png?format=750w",
                    -6.8331128,
                    107.6048483
                ),
                Turtle(
                    "Rabbit Town",
                    "Latin name",
                    "Tidak dilindungi",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRrcsDrhi0zMrpg46TWprj3iNo87nUURujNGQ&usqp=CAU",
                    -6.8668408,
                    107.608081
                ),
                Turtle(
                    "Floating Market Lembang",
                    "Latin name",
                    "dilindungi",
                    "https://www.fisheries.noaa.gov/s3/styles/original/s3/2021-07/640x427-Turtle_Green_NOAAFisheries.png",
                    -6.8168954,
                    107.6151046
                ),
                Turtle(
                    "The Great Asia Africa",
                    "Latin name",
                    "dilindungi",
                    "https://images.squarespace-cdn.com/content/v1/59cae0d6be42d63f64cf6dd2/1542902265247-7TO79BP2LWFFEALYZJK9/Witherington2018-10.png?format=750w",
                    -6.8331128,
                    107.6048483
                ),
                Turtle(
                    "Rabbit Town",
                    "Latin name",
                    "Tidak dilindungi",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRrcsDrhi0zMrpg46TWprj3iNo87nUURujNGQ&usqp=CAU",
                    -6.8668408,
                    107.608081
                ),
                Turtle(
                    "Floating Market Lembang",
                    "Latin name",
                    "dilindungi",
                    "https://www.fisheries.noaa.gov/s3/styles/original/s3/2021-07/640x427-Turtle_Green_NOAAFisheries.png",
                    -6.8168954,
                    107.6151046
                ),
                Turtle(
                    "The Great Asia Africa",
                    "Latin name",
                    "dilindungi",
                    "https://images.squarespace-cdn.com/content/v1/59cae0d6be42d63f64cf6dd2/1542902265247-7TO79BP2LWFFEALYZJK9/Witherington2018-10.png?format=750w",
                    -6.8331128,
                    107.6048483
                ),
                Turtle(
                    "Rabbit Town",
                    "Latin name",
                    "Tidak dilindungi",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRrcsDrhi0zMrpg46TWprj3iNo87nUURujNGQ&usqp=CAU",
                    -6.8668408,
                    107.608081
                ),
            )
        }catch (e: Exception){
            histories = null
        }

        return histories
    }
}