package ru.netology.nerecipes.adapter

import ru.netology.nerecipes.data.Recipe

interface RecipeInteractionListener {

fun onFavClicked(recipe: Recipe)
fun onRemoveClicked(recipe: Recipe)
fun onEditClicked(recipe: Recipe)
fun onRecipeCardClicked(recipe: Recipe)
fun onRecipeItemClicked(recipe: Recipe)
}