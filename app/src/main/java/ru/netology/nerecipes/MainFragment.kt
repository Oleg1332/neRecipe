package ru.netology.nerecipes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipes.adapter.RecipesAdapter
import ru.netology.nerecipes.data.Category
import ru.netology.nerecipes.data.Recipe
import ru.netology.nerecipes.databinding.MainFragmentBinding
import ru.netology.nerecipes.fragments.RecipeCreationFragment
import ru.netology.nerecipes.fragments.RecipeFilterFragment
import ru.netology.nerecipes.viewModel.RecipeViewModel

class MainFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.recipeViewEvent.observe(this) { recipeCardId ->
            val direction =
                MainFragmentDirections.actionMainFragmentToRecipeViewFragment(recipeCardId)
            findNavController().navigate(direction)
        }
        viewModel.navigateToRecipeCreationFragmentEvent.observe(this) { recipe ->
            val direction = MainFragmentDirections.actionMainFragmentToRecipeCreationFragment(recipe)
            findNavController().navigate(direction)
        }
        viewModel.navigateToRecipeFilterFragmentEvent.observe(this) {
            val direction =
                MainFragmentDirections.actionMainFragmentToRecipeFilterFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onResume() {
        super.onResume()
        setFragmentResultListener(
            requestKey = RecipeCreationFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != RecipeCreationFragment.REQUEST_KEY) return@setFragmentResultListener
            val newRecipe = bundle.getParcelable<Recipe>(
                RecipeCreationFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newRecipe)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = MainFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        val adapter = RecipesAdapter(viewModel)
        binding.recipesRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }
        binding.fab.setOnClickListener {
            viewModel.onAddButtonClicked()
        }
        binding.filterButton.setOnClickListener {
            viewModel.onFilterClicked()
        }
        setFragmentResultListener(
            requestKey = RecipeFilterFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != RecipeFilterFragment.REQUEST_KEY) return@setFragmentResultListener
            val category = bundle.getParcelableArrayList<Category>(
                RecipeFilterFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            viewModel.onSetFilterClicked(category)
        }

        val searchItem = binding.search
        searchItem.imeOptions = EditorInfo.IME_ACTION_DONE
        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchQuery.value = newText
                return true
            }
        })

    }.root

}

