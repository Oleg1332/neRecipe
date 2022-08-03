package ru.netology.nerecipes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipes.adapter.RecipesAdapter
import ru.netology.nerecipes.data.Recipe
import ru.netology.nerecipes.databinding.FragmentFavBinding

import ru.netology.nerecipes.viewModel.RecipeViewModel

class FavFragment : Fragment() {

    private val favouriteRecipeViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFavBinding.inflate(layoutInflater, container, false).also { binding ->
        val adapter = RecipesAdapter(favouriteRecipeViewModel)
        binding.recipesRecycler.adapter = adapter

        favouriteRecipeViewModel.data.observe(viewLifecycleOwner) { recipes ->

            val favouriteRecipes = recipes.filter { it.fav }
            adapter.submitList(favouriteRecipes)

            val emptyList = recipes.none { it.fav }
            binding.textEmptyList.visibility =
                if (emptyList) View.VISIBLE else View.GONE
            binding.iconEmptyList.visibility =
                if (emptyList) View.VISIBLE else View.GONE
        }

        favouriteRecipeViewModel.recipeViewEvent.observe(viewLifecycleOwner) { recipeCardId ->
            val direction =
                FavFragmentDirections.actionFavFragmentToRecipeViewFragment(
                    recipeCardId
                )
            findNavController().navigate(direction)
        }

        favouriteRecipeViewModel.navigateToRecipeCreationFragmentEvent.observe(viewLifecycleOwner) { recipe ->
            val direction =
                FavFragmentDirections.actionFavFragmentToRecipeCreationFragment(
                    recipe
                )
            findNavController().navigate(direction)
        }
    }.root

    override fun onResume() {
        super.onResume()
        setFragmentResultListener(
            requestKey = RecipeCreationFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != RecipeCreationFragment.REQUEST_KEY) return@setFragmentResultListener
            val newRecipe = bundle.getParcelable<Recipe>(
                RecipeCreationFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            favouriteRecipeViewModel.onSaveButtonClicked(newRecipe)
        }
    }
}
