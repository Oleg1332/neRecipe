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
import ru.netology.nerecipes.R
import ru.netology.nerecipes.data.Category
import ru.netology.nerecipes.data.Recipe
import ru.netology.nerecipes.data.RecipeRepository
import ru.netology.nerecipes.databinding.FragmentRecipeCreationBinding
import ru.netology.nerecipes.viewModel.RecipeViewModel


class RecipeCreationFragment : Fragment() {

    private val args by navArgs<RecipeCreationFragmentArgs>()

    private val recipeCreationViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentRecipeCreationBinding.inflate(layoutInflater, container, false).also { binding ->

        val thisRecipe = args.currentRecipe
        if (thisRecipe != null) {
            with(binding) {
                categoryRecipeCheckbox.check(R.id.checkboxAs)
                categoryRecipeCheckbox.check(R.id.checkboxEu)
                categoryRecipeCheckbox.check(R.id.checkboxNa)
                categoryRecipeCheckbox.check(R.id.checkboxRu)
                recipeName.setText(thisRecipe.name)
                recipeEnterField.setText(thisRecipe.recipe)
                txtRecipeDescriptionInput.setText(thisRecipe.description)
                txtTimeInput.setText(thisRecipe.time)
            }
        }

        binding.recipeName.requestFocus()

        binding.categoryRecipeCheckbox.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.checkboxRu -> Category.Russian.toString()
                R.id.checkboxNa -> Category.American.toString()
                R.id.checkboxEu -> Category.European.toString()
                R.id.checkboxAs -> Category.Asian.toString()
            }
        }

        binding.btnCreateRecipe.setOnClickListener {
            onOkButtonClicked(binding)
        }
        binding.cancelButton.setOnClickListener {
            onCancelClicked(binding)
        }
    }.root

    private fun onCancelClicked(binding: FragmentRecipeCreationBinding) {
        val currentRecipe = Recipe(
            id = args.currentRecipe?.id ?: RecipeRepository.NEW_RECIPE_ID,
            name = binding.recipeName.text.toString(),
            description = binding.txtRecipeDescriptionInput.text.toString(),
            recipe = binding.recipeEnterField.text.toString(),
            time = binding.txtTimeInput.text.toString(),
            category = Category.Asian,
            author = "Me"
        )
    recipeCreationViewModel.onCancelClicked(currentRecipe)
    findNavController().navigateUp()
    }

    private fun onOkButtonClicked(binding: FragmentRecipeCreationBinding) {
        val currentRecipe = Recipe(
            id = args.currentRecipe?.id ?: RecipeRepository.NEW_RECIPE_ID,
            name = binding.recipeName.text.toString(),
            description = binding.txtRecipeDescriptionInput.text.toString(),
            recipe = binding.recipeEnterField.text.toString(),
            time = binding.txtTimeInput.text.toString(),
            category = Category.Asian,
            author = "Me"
        )
        if (emptyFieldsCheck(recipe = currentRecipe)) {
            val resultBundle = Bundle(1)
            resultBundle.putParcelable(RESULT_KEY, currentRecipe)
            setFragmentResult(REQUEST_KEY, resultBundle)
        } else return
        findNavController().popBackStack()
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
        const val RESULT_KEY = "recipeNewContent"
    }
}