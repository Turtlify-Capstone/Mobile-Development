package com.bangkit.turtlify.data.repository

data class Turtle(
    val id: Int,
    val name: String,
    val latinName: String,
    val status: String,
    val imageUrl: String,
    val food: String,
    val lifeExpectancy: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)
class EncyclopediaRepository {
    val encyclopediaList = listOf(
        Turtle(
            1,
            "Floating Market Lembang",
            "Latin name",
            "dilindungi",
            "https://www.fisheries.noaa.gov/s3/styles/original/s3/2021-07/640x427-Turtle_Green_NOAAFisheries.png",
            "2-3 times a day",
            "2-3 years",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus bibendum non ligula vel rutrum. Donec in iaculis lacus, vitae egestas ante. Donec sit amet diam sed dui euismod laoreet. Maecenas a dui mi. Sed hendrerit purus dolor, rutrum accumsan justo pretium sit amet. In lobortis vel odio eget consectetur. Nunc iaculis arcu ut magna dapibus, non sodales metus ultricies. Fusce eget quam accumsan, iaculis nisl vel, dictum augue. Quisque dictum lectus ipsum, sed gravida erat aliquam eget. Vestibulum lobortis suscipit lobortis. Pellentesque a massa purus. Ut venenatis posuere posuere.",
            -6.8168954,
            107.6151046
        ),
        Turtle(
            2,
            "The Great Asia Africa",
            "Latin name",
            "tidak dilindungi",
            "https://images.squarespace-cdn.com/content/v1/59cae0d6be42d63f64cf6dd2/1542902265247-7TO79BP2LWFFEALYZJK9/Witherington2018-10.png?format=750w",
            "2-3 times a day",
            "2-3 years",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus bibendum non ligula vel rutrum. Donec in iaculis lacus, vitae egestas ante. Donec sit amet diam sed dui euismod laoreet. Maecenas a dui mi. Sed hendrerit purus dolor, rutrum accumsan justo pretium sit amet. In lobortis vel odio eget consectetur. Nunc iaculis arcu ut magna dapibus, non sodales metus ultricies. Fusce eget quam accumsan, iaculis nisl vel, dictum augue. Quisque dictum lectus ipsum, sed gravida erat aliquam eget. Vestibulum lobortis suscipit lobortis. Pellentesque a massa purus. Ut venenatis posuere posuere.",
            -6.8331128,
            107.6048483
        ),
        Turtle(
            3,
            "Floating Market Lembang",
            "Latin name",
            "dilindungi",
            "https://www.fisheries.noaa.gov/s3/styles/original/s3/2021-07/640x427-Turtle_Green_NOAAFisheries.png",
            "2-3 times a day",
            "2-3 years",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus bibendum non ligula vel rutrum. Donec in iaculis lacus, vitae egestas ante. Donec sit amet diam sed dui euismod laoreet. Maecenas a dui mi. Sed hendrerit purus dolor, rutrum accumsan justo pretium sit amet. In lobortis vel odio eget consectetur. Nunc iaculis arcu ut magna dapibus, non sodales metus ultricies. Fusce eget quam accumsan, iaculis nisl vel, dictum augue. Quisque dictum lectus ipsum, sed gravida erat aliquam eget. Vestibulum lobortis suscipit lobortis. Pellentesque a massa purus. Ut venenatis posuere posuere.",
            -6.8168954,
            107.6151046
        ),
        Turtle(
            4,
            "The Great Asia Africa",
            "Latin name",
            "tidak dilindungi",
            "https://images.squarespace-cdn.com/content/v1/59cae0d6be42d63f64cf6dd2/1542902265247-7TO79BP2LWFFEALYZJK9/Witherington2018-10.png?format=750w",
            "2-3 times a day",
            "2-3 years",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus bibendum non ligula vel rutrum. Donec in iaculis lacus, vitae egestas ante. Donec sit amet diam sed dui euismod laoreet. Maecenas a dui mi. Sed hendrerit purus dolor, rutrum accumsan justo pretium sit amet. In lobortis vel odio eget consectetur. Nunc iaculis arcu ut magna dapibus, non sodales metus ultricies. Fusce eget quam accumsan, iaculis nisl vel, dictum augue. Quisque dictum lectus ipsum, sed gravida erat aliquam eget. Vestibulum lobortis suscipit lobortis. Pellentesque a massa purus. Ut venenatis posuere posuere.",
            -6.8331128,
            107.6048483
        ),
        Turtle(
            5,
            "Floating Market Lembang",
            "Latin name",
            "dilindungi",
            "https://www.fisheries.noaa.gov/s3/styles/original/s3/2021-07/640x427-Turtle_Green_NOAAFisheries.png",
            "2-3 times a day",
            "2-3 years",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus bibendum non ligula vel rutrum. Donec in iaculis lacus, vitae egestas ante. Donec sit amet diam sed dui euismod laoreet. Maecenas a dui mi. Sed hendrerit purus dolor, rutrum accumsan justo pretium sit amet. In lobortis vel odio eget consectetur. Nunc iaculis arcu ut magna dapibus, non sodales metus ultricies. Fusce eget quam accumsan, iaculis nisl vel, dictum augue. Quisque dictum lectus ipsum, sed gravida erat aliquam eget. Vestibulum lobortis suscipit lobortis. Pellentesque a massa purus. Ut venenatis posuere posuere.",
            -6.8168954,
            107.6151046
        ),
        Turtle(
            6,
            "The Great Asia Africa",
            "Latin name",
            "tidak dilindungi",
            "2-3 times a day",
            "2-3 years",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus bibendum non ligula vel rutrum. Donec in iaculis lacus, vitae egestas ante. Donec sit amet diam sed dui euismod laoreet. Maecenas a dui mi. Sed hendrerit purus dolor, rutrum accumsan justo pretium sit amet. In lobortis vel odio eget consectetur. Nunc iaculis arcu ut magna dapibus, non sodales metus ultricies. Fusce eget quam accumsan, iaculis nisl vel, dictum augue. Quisque dictum lectus ipsum, sed gravida erat aliquam eget. Vestibulum lobortis suscipit lobortis. Pellentesque a massa purus. Ut venenatis posuere posuere.",
            "https://images.squarespace-cdn.com/content/v1/59cae0d6be42d63f64cf6dd2/1542902265247-7TO79BP2LWFFEALYZJK9/Witherington2018-10.png?format=750w",
            -6.8331128,
            107.6048483
        ),
        Turtle(
            7,
            "The Great Asia Africa",
            "Latin name",
            "tidak dilindungi",
            "2-3 times a day",
            "2-3 years",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus bibendum non ligula vel rutrum. Donec in iaculis lacus, vitae egestas ante. Donec sit amet diam sed dui euismod laoreet. Maecenas a dui mi. Sed hendrerit purus dolor, rutrum accumsan justo pretium sit amet. In lobortis vel odio eget consectetur. Nunc iaculis arcu ut magna dapibus, non sodales metus ultricies. Fusce eget quam accumsan, iaculis nisl vel, dictum augue. Quisque dictum lectus ipsum, sed gravida erat aliquam eget. Vestibulum lobortis suscipit lobortis. Pellentesque a massa purus. Ut venenatis posuere posuere.",
            "https://images.squarespace-cdn.com/content/v1/59cae0d6be42d63f64cf6dd2/1542902265247-7TO79BP2LWFFEALYZJK9/Witherington2018-10.png?format=750w",
            -6.8331128,
            107.6048483
        ),
        Turtle(
            8,
            "Floating Market Lembang",
            "Latin name",
            "dilindungi",
            "https://www.fisheries.noaa.gov/s3/styles/original/s3/2021-07/640x427-Turtle_Green_NOAAFisheries.png",
            "2-3 times a day",
            "2-3 years",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus bibendum non ligula vel rutrum. Donec in iaculis lacus, vitae egestas ante. Donec sit amet diam sed dui euismod laoreet. Maecenas a dui mi. Sed hendrerit purus dolor, rutrum accumsan justo pretium sit amet. In lobortis vel odio eget consectetur. Nunc iaculis arcu ut magna dapibus, non sodales metus ultricies. Fusce eget quam accumsan, iaculis nisl vel, dictum augue. Quisque dictum lectus ipsum, sed gravida erat aliquam eget. Vestibulum lobortis suscipit lobortis. Pellentesque a massa purus. Ut venenatis posuere posuere.",
            -6.8168954,
            107.6151046
        ),
        Turtle(
            9,
            "The Great Asia Africa",
            "Latin name",
            "tidak dilindungi",
            "2-3 times a day",
            "2-3 years",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus bibendum non ligula vel rutrum. Donec in iaculis lacus, vitae egestas ante. Donec sit amet diam sed dui euismod laoreet. Maecenas a dui mi. Sed hendrerit purus dolor, rutrum accumsan justo pretium sit amet. In lobortis vel odio eget consectetur. Nunc iaculis arcu ut magna dapibus, non sodales metus ultricies. Fusce eget quam accumsan, iaculis nisl vel, dictum augue. Quisque dictum lectus ipsum, sed gravida erat aliquam eget. Vestibulum lobortis suscipit lobortis. Pellentesque a massa purus. Ut venenatis posuere posuere.",
            "https://images.squarespace-cdn.com/content/v1/59cae0d6be42d63f64cf6dd2/1542902265247-7TO79BP2LWFFEALYZJK9/Witherington2018-10.png?format=750w",
            -6.8331128,
            107.6048483
        ),
    )
    suspend fun getTurtlesEncyclopedia(): List<Turtle>? {
        var encyclopedia:List<Turtle>?

        try {
            encyclopedia = encyclopediaList
        }catch (e: Exception){
            encyclopedia = null
        }

        return encyclopedia
    }

    suspend fun getEncyclopediaDetail(turtleId: Int): Turtle? {

        var encyclopedia: Turtle? = try {
            encyclopediaList.firstOrNull { turtle -> turtle.id == turtleId }
        }catch (e: Exception){
            null
        }

        return encyclopedia
    }
}