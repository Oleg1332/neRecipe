package ru.netology.nerecipes.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipes.data.Recipe
import ru.netology.nerecipes.data.RecipeRepository

class InMemoryRecipeRepository : RecipeRepository {
    private var nextId = 1L
    private var recipes = listOf(
            Recipe(
                id = nextId++,
                author = "Me",
                category = "Russian",
                name = "ris",
                description = "horoshiy",
                time = "24 min",
                recipe = "svarit' ris",
                fav = false,
                picture = ""
            ),
        Recipe(
            id = nextId++,
            author = "Me",
            category = "Asian",
            name = "ris",
            description = "ochen' horoshiy",
            time = "56 min",
            recipe = "pozharit' ris",
            fav = false,
            picture = ""
        )
    )
    override val data = MutableLiveData(recipes)


    override fun favorite(recipeId: Long) {
        data.value = recipes.map {
            if (it.id != recipeId) it
            else it.copy(fav = !it.fav)
        }
    }

    override fun delete(recipeId: Long) {
        recipes =
            recipes.filter { it.id != recipeId }
        data.value = recipes
    }

    override fun save(recipe: Recipe) {
        if (recipe.id == RecipeRepository.NEW_RECIPE_ID) insert(recipe) else update(recipe)
    }

    private fun update(recipe: Recipe) {
        recipes = recipes.map {
            if (it.id == recipe.id) recipe else it
        }
        data.value = recipes
    }

    private fun insert(recipe: Recipe) {
        recipes =
            listOf(
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

}