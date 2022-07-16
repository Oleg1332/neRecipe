package ru.netology.nerecipes.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipes.Util.SingleLiveEvent
import ru.netology.nerecipes.adapter.RecipeInteractionListener
import ru.netology.nerecipes.data.Category
import ru.netology.nerecipes.data.Recipe
import ru.netology.nerecipes.data.RecipeRepository
import ru.netology.nerecipes.impl.SharedPrefsRecipeRepository


class RecipeViewModel (
    application: Application
) : AndroidViewModel(application), RecipeInteractionListener {

    private val repository: RecipeRepository = SharedPrefsRecipeRepository(application)

    val data by repository::data
    val recipeViewEvent = SingleLiveEvent<Long>()
    val navigateToRecipeCreationFragmentEvent = SingleLiveEvent<Recipe?>()
    val navigateToRecipeFilterFragmentEvent = SingleLiveEvent<Recipe?>()
    private val currentRecipe = MutableLiveData<Recipe?>(null)
    val searchQuery: MutableLiveData<String> = MutableLiveData()

    fun onSaveButtonClicked(recipe: Recipe) {
        if (recipe.recipe.isBlank() && recipe.name.isBlank()) return
        val newRecipe = currentRecipe.value?.copy(
            recipe = recipe.recipe,
            name = recipe.name,
            category = recipe.category
        ) ?: Recipe(
            id = RecipeRepository.NEW_RECIPE_ID,
            author = "Me",
            name = recipe.name,
            category = recipe.category,
            recipe = recipe.recipe,
            time = recipe.time,
            description = recipe.description
        )
        repository.save(newRecipe)
        currentRecipe.value = null
    }

    fun onAddButtonClicked() {
        navigateToRecipeCreationFragmentEvent.call()
    }
    fun onFilterClicked() {
        navigateToRecipeFilterFragmentEvent.call()
    }
    override fun onFavClicked(recipe: Recipe) {
        repository.favorite(recipe.id)
    }

    override fun onRemoveClicked(recipe: Recipe) {
        repository.delete(recipe.id)
    }

    override fun onEditClicked(recipe: Recipe) {
        currentRecipe.value = recipe
        navigateToRecipeCreationFragmentEvent.value = recipe
    }

    override fun onRecipeCardClicked(recipe: Recipe) {
        recipeViewEvent.value = recipe.id
    }

    fun onCancelClicked(recipe: Recipe) {
        currentRecipe.value = null
    }

    override fun onRecipeItemClicked(recipe: Recipe) {
        navigateToRecipeCreationFragmentEvent.value = recipe
    }
    fun searchRecipe(recipeName: String) {
        repository.search(recipeName)
    }

    fun clearFilter() {
        repository.getAllRecipes()
    }

    fun showRecipesByCategories(category: Category) {
        repository.getCategory(category)

    }
}