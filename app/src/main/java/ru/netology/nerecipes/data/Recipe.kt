package  ru.netology.nerecipes.data
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Long,
    val author: String,
    val category: String,
    val name: String,
    val description: String,
    val time: String,
    val recipe: String,
    val fav: Boolean = false,
    val picture: String = ""
) : Parcelable {

    @Parcelize
    enum class Category(
        val key: String,
        val isChosen: Boolean = true
    ) : Parcelable {
        European("Европейская",true),
        Asian("Азиатская",true),
        American("Американская",true),
        Russian("Русская",true)
    }
}

annotation class Parcelize
