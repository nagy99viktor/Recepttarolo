package hu.hazi.recepttarolo.recipe.pager

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.hazi.recepttarolo.R
import hu.hazi.recepttarolo.recipe.Database
import hu.hazi.recepttarolo.recipe.Recipe
import hu.hazi.recepttarolo.recipe.ingredient.Ingredient
import hu.hazi.recepttarolo.recipe.ingredient.IngredientAdapter
import hu.hazi.recepttarolo.recipe.ingredient.NewIngredientDialogFragment
import hu.hazi.recepttarolo.recipe.shoppinglist.Item
import kotlinx.android.synthetic.main.fragment_recipe.*
import kotlin.concurrent.thread


class RecipeFragment(private var recipe: Recipe) : Fragment(), NewIngredientDialogFragment.NewIngredientDialogListener, IngredientAdapter.IngredientClickListener,
    EditRecipeDialogFragment.EditRecipeDialogListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: IngredientAdapter
    private var recipeDataHolder: RecipeDataHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    private fun initRecyclerView() {
        recyclerView = IngredientRecyclerView
        adapter = IngredientAdapter(this)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        recyclerView.adapter = adapter

    }

    private fun loadItemsInBackground() {
        thread {
            val items =  Database.getInstance(requireContext()).ingredientDao().getByRecipeId(recipe.id)
            this.activity?.runOnUiThread {
                adapter.update(items)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        newIngredientFab.setOnClickListener{

            fragmentManager?.let { it1 ->
                NewIngredientDialogFragment(this, null).show(
                    it1.beginTransaction(),
                    NewIngredientDialogFragment.TAG
                )
            }
        }
        toTheShoppingList.setOnClickListener{
            thread {
                val ingredients =  Database.getInstance(requireContext()).ingredientDao().getByRecipeId(
                    recipe.id
                )

                for (ingredient: Ingredient in ingredients){
                    Database.getInstance(requireContext()).itemDao().insert(
                        Item(
                            null,
                            ingredient.description,
                            false
                        )
                    )
                }
            }

        }

        RecipeEditButton.setOnClickListener{
            fragmentManager?.let { it1 ->
                EditRecipeDialogFragment(this, recipe).show(
                    it1.beginTransaction(),
                    EditRecipeDialogFragment.TAG
                )
            }
        }
        DescriptionTextView.movementMethod = ScrollingMovementMethod()
        initRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayWeatherData()
    }
    private fun displayWeatherData() {
        NameTextView.text = recipe.name
        DescriptionTextView.text = recipe.description
    }

    override fun onIngredientCreated(newItem: Ingredient) {
        thread {
            newItem.recipeId = recipe.id
            val newId =  Database.getInstance(requireContext()).ingredientDao().insert(newItem)
            val newIngredientItem = newItem.copy(
                id = newId
            )

            this.activity?.runOnUiThread {
                    adapter.addItem(newIngredientItem)
            }
        }
    }

    override fun onIngredientEdited(newItem: Ingredient) {
        thread {
            Database.getInstance(requireContext()).ingredientDao().update(newItem)
            loadItemsInBackground();
        }
    }


    override fun onItemDeleted(item: Ingredient) {
        thread {
            Database.getInstance(requireContext()).ingredientDao().deleteItem(item)
            loadItemsInBackground();
        }
    }

    override fun onItemEdit(item: Ingredient) {
        fragmentManager?.let { it1 ->
            NewIngredientDialogFragment(this, item).show(
                it1.beginTransaction(),
                NewIngredientDialogFragment.TAG
            )
        }
    }

    override fun onRecipeEdited(editedRecipe: Recipe) {
        recipe.name= editedRecipe.name
        recipe.description= editedRecipe.description
        recipe.category= editedRecipe.category
        displayWeatherData()
        thread {
            Database.getInstance(requireContext()).recipeDao().update(recipe)
        }
    }

}