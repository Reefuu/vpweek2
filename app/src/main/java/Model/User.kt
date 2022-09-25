package Model

open class User(
    title: String,
    rating: String,
    genre: String,
    imageUri: String
) {
    var title: String = title
    var genre: String = genre
    var rating: String = rating
    var imageUri: String = imageUri

    fun kasiMakan(s: Biji): String {
        return "Aku makannya biji"
    }

    fun kasiMakan(i: Rumput): String {
        return "Aku makannya rumput"
    }

    open fun pegang():String {
        return ""
    }


}