package  ru.netology.nerecipes.data
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Recipe(
    val id: Long,
    val author: String,
    val category: Category,
    val name: String,
    val description: String,
    val time: String,
    val recipe: String,
    val fav: Boolean = false,
    val picture: String = ""
) : Parcelable

    @Serializable
    @Parcelize
    enum class Category(
        val label: String
    ) : Parcelable {
        European("European".toString()),
        Asian("Asian".toString()),
        American("American".toString()),
        Russian("Russian".toString())
    }



