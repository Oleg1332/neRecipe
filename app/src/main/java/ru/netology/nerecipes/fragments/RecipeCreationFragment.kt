package ru.netology.nerecipes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nerecipes.data.Recipe
import ru.netology.nerecipes.data.RecipeRepository
import ru.netology.nerecipes.databinding.FragmentRecipeCreationBinding
import ru.netology.nerecipes.viewModel.RecipeViewModel


class RecipeCreationFragment : Fragment() {

    private val args by navArgs<RecipeCreationFragmentArgs>()
    private var categoryChoice = ""

    private val recipeCreationViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentRecipeCreationBinding.inflate(layoutInflater, container, false).also { binding ->

        val thisRecipe = args.currentRecipe
        if (thisRecipe != null) {
            binding.recipeName.setText(thisRecipe.name)
            binding.recipeEnterField.setText(thisRecipe.recipe)
            binding.txtRecipeDescriptionInput.setText(thisRecipe.description)
            binding.txtTimeInput.setText(thisRecipe.time)
        }

        binding.recipeName.requestFocus()

        binding.btnCreateRecipe.setOnClickListener {
            onOkButtonClicked(binding)
        }
    }.root

    private fun onOkButtonClicked(binding: FragmentRecipeCreationBinding) {
        val currentRecipe = Recipe(
            id = args.currentRecipe?.id ?: RecipeRepository.NEW_RECIPE_ID,
            name = binding.recipeName.text.toString(),
            description = binding.txtRecipeDescriptionInput.text.toString(),
            recipe = binding.recipeEnterField.text.toString(),
            time = binding.txtTimeInput.text.toString(),
            category = categoryChoice,
            author = "Me"
        )
        if (emptyFieldsCheck(recipe = currentRecipe)) {
            val resultBundle = Bundle(1)
            resultBundle.putParcelable(RESULT_KEY, currentRecipe)
            setFragmentResult(REQUEST_KEY, resultBundle)
            findNavController().popBackStack()
        } else return

    }

    private fun emptyFieldsCheck(recipe: Recipe): Boolean {
        return if (recipe.name.isBlank()
            || recipe.recipe.isBlank()
            //|| recipe.category.isBlank()
            || recipe.description.isBlank()
            || recipe.time.isBlank()
        ) {
            Toast.makeText(activity, "Заполните все поля", Toast.LENGTH_LONG).show()
            false
        } else {
            true
        }
    }


    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "postNewContent"
    }
}