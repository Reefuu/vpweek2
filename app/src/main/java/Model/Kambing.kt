import Model.User

open class Kambing(title: String, rating: String, genre: String, imageUri: String) :
    User(title, rating, genre, imageUri) {
    override fun pegang():String {
        return "MBEEKEKEKEKKEKEK........"
    }
}