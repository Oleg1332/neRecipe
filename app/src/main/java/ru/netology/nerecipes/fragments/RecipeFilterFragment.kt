package ru.netology.nerecipes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipes.data.Category
import ru.netology.nerecipes.databinding.FragmentRecipeFilterBinding
import ru.netology.nerecipes.viewModel.RecipeViewModel


class RecipeFilterFragment : Fragment() {


    private val categoryFilterViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentRecipeFilterBinding.inflate(layoutInflater, container, false).also { binding ->


        with(binding) {
            checkboxEu.text = Category.European.toString()
            checkboxAs.text = Category.Asian.toString()
            checkboxNa.text = Category.American.toString()
            checkboxRu.text = Category.Russian.toString()

            binding.btnSetFilter.setOnClickListener {
                onOkButtonClicked(binding)
            }
        }
    }.root

    private fun onOkButtonClicked(binding: FragmentRecipeFilterBinding) {
        var checkedCount = 0
        val numberOfFilters = 7

        if (!binding.checkboxEu.isChecked) {
            ++checkedCount
            categoryFilterViewModel.showRecipesByCategories(Category.European)
        }
        if (!binding.checkboxAs.isChecked) {
            ++checkedCount
            categoryFilterViewModel.showRecipesByCategories(Category.Asian)
        }
        if (!binding.checkboxNa.isChecked) {
            ++checkedCount
            categoryFilterViewModel.showRecipesByCategories(Category.American)
        }
        if (!binding.checkboxRu.isChecked) {
            ++checkedCount
            categoryFilterViewModel.showRecipesByCategories(Category.Russian)
        }
        findNavController().popBackStack()
    }


    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "newContent"
    }

}