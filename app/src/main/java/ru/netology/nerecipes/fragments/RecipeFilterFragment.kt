package ru.netology.nerecipes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipes.adapter.showCategories
import ru.netology.nerecipes.data.Category
import ru.netology.nerecipes.databinding.FragmentRecipeFilterBinding
import ru.netology.nerecipes.viewModel.RecipeViewModel


class RecipeFilterFragment : Fragment() {


    private val filterViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentRecipeFilterBinding.inflate(layoutInflater, container, false).also { binding ->


        with(binding) {
            checkboxEu.text = checkboxEu.context.showCategories(Category.European)
            checkboxAs.text = checkboxAs.context.showCategories(Category.Asian)
            checkboxNa.text = checkboxNa.context.showCategories(Category.American)
            checkboxRu.text = checkboxRu.context.showCategories(Category.Russian)

            binding.setFilter.setOnClickListener {
                onOkButtonClicked(binding)
            }
        }
    }.root

    private fun onOkButtonClicked(binding: FragmentRecipeFilterBinding) {
        val categoryList = arrayListOf<Category>()
        var checkedCount = 4
        val nothingIsChecked = 0

        if (binding.checkboxEu.isChecked) {
            categoryList.add(Category.European)
            filterViewModel.setCategoryFilter = true
        } else {
            --checkedCount
        }
        if (binding.checkboxAs.isChecked) {
            categoryList.add(Category.Asian)
            filterViewModel.setCategoryFilter = true
        } else {
            --checkedCount
        }
        if (binding.checkboxNa.isChecked) {
            categoryList.add(Category.American)
            filterViewModel.setCategoryFilter = true
        } else {
            --checkedCount
        }
        if (binding.checkboxRu.isChecked) {
            categoryList.add(Category.Russian)
            filterViewModel.setCategoryFilter = true
        } else {
            --checkedCount
        }
        if (checkedCount == nothingIsChecked) {
            Toast.makeText(activity, "Put some filters", Toast.LENGTH_LONG).show()
        } else {
            filterViewModel.showRecipesByCategories(categoryList)
            val resultBundle = Bundle(1)
            resultBundle.putParcelableArrayList(CHECKBOX_KEY, categoryList)
            setFragmentResult(CHECKBOX_KEY, resultBundle)
            findNavController().popBackStack()
        }
    }


    companion object {
        const val CHECKBOX_KEY = "checkBoxContent"
    }

}