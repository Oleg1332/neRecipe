package ru.netology.nerecipes.data

import androidx.lifecycle.LiveData

interface RecipeRepository {
    val data: LiveData<List<Recipe>>

    fun favorite(recipeId: Long)
    fun delete(recipeId: Long)
    fun save(recipe: Recipe)
    fun search(recipeName: String)
    fun getAllRecipes()
    fun getCategory(category: Category)
    companion object {
        const val NEW_RECIPE_ID = 0L
    }
}