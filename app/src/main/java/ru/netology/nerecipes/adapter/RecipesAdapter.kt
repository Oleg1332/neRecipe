package ru.netology.nerecipes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nerecipes.R
import ru.netology.nerecipes.data.Category
import ru.netology.nerecipes.data.Recipe
import ru.netology.nerecipes.databinding.RecipeBinding

class RecipesAdapter(
    private val interactionListener: RecipeInteractionListener
) : ListAdapter<Recipe, RecipesAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: RecipeBinding,
        listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.recipe_menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(recipe)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(recipe)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.options.setOnClickListener { popupMenu.show() }
        }

        init {
            binding.recipeName.setOnClickListener { listener.onRecipeCardClicked(recipe) }
            binding.recipeTime.setOnClickListener { listener.onRecipeCardClicked(recipe) }
            binding.recipeIcon.setOnClickListener { listener.onRecipeCardClicked(recipe) }
        }

        init {
            binding.favorite.setOnClickListener { listener.onFavClicked(recipe) }
            itemView.setOnClickListener { listener.onRecipeItemClicked(recipe) }
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            with(binding) {
                recipeTime.text = recipe.time
                recipeName.text = recipe.name
                favorite.isChecked = recipe.fav
            }
        }

    }

    private object DiffCallBack : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem == newItem
    }
}

fun Context.showCategories(category: Category): String {
    return when (category) {
        Category.European -> getString(R.string.european)
        Category.Asian -> getString(R.string.asian)
        Category.American -> getString(R.string.american)
        Category.Russian -> getString(R.string.russian)
    }

}
