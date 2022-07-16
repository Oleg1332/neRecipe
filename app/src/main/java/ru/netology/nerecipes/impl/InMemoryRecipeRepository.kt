package ru.netology.nerecipes.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipes.data.Category
import ru.netology.nerecipes.data.Recipe
import ru.netology.nerecipes.data.RecipeRepository

class InMemoryRecipeRepository : RecipeRepository {
    override val data = MutableLiveData(
        List(GENERATED_RECIPES_AMOUNT) { index ->
            Recipe(
                id = index + 1L,
                author = "Oleg",
                name = "plov",
                recipe = "Some random content $index",
                description = "something",
                category = Category.Asian,
                time = "34min"
            )
        }
    )

    private var nextId = GENERATED_RECIPES_AMOUNT.toLong()

    private val recipes get() = checkNotNull(data.value) {
        "Data value should not be null"
    }

    override fun favorite(recipeId: Long) {
        data.value = recipes.map {
            if (it.id != recipeId) it
            else it.copy(fav = !it.fav)
        }
    }

    override fun delete(recipeId: Long) {
        data.value = recipes.filter { it.id != recipeId }
        data.value = recipes
    }

    override fun save(recipe: Recipe) {
        if (recipe.id == RecipeRepository.NEW_RECIPE_ID) insert(recipe) else update(recipe)
    }

    private fun update(recipe: Recipe) {
        data.value = recipes.map {
            if (it.id == recipe.id) recipe else it
        }
        data.value = recipes
    }

    private fun insert(recipe: Recipe) {
        data.value = listOf(
                recipe.copy(
                    id = nextId++
                )
            ) + recipes
        data.value = recipes
    }
    override fun search(recipeName: String) {
        recipes.find {
            it.name == recipeName
        } ?: throw RuntimeException("Ничего не найдено")
        data.value = recipes
    }

    override fun getAllRecipes() {
        data.value = recipes
    }

    override fun getCategory(category: Category) {
        recipes.find {
            it.category == category
        }
        data.value = recipes
    }

    private companion object {
        const val GENERATED_RECIPES_AMOUNT = 1000
    }
}