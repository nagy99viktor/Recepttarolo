package hu.hazi.recepttarolo.recipe.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.hazi.recepttarolo.MainActivity
import hu.hazi.recepttarolo.R
import hu.hazi.recepttarolo.recipe.Recipe
import hu.hazi.recepttarolo.recipe.RecipeAdapter
import hu.hazi.recepttarolo.recipe.ingredient.Ingredient
import hu.hazi.recepttarolo.recipe.ingredient.IngredientAdapter
import hu.hazi.recepttarolo.recipe.ingredient.NewIngredientDialogFragment
import kotlinx.android.synthetic.main.fragment_recipe.*
import kotlin.concurrent.thread


class RecipeFragment(private var recipe: Recipe) : Fragment(), NewIngredientDialogFragment.NewIngredientDialogListener, IngredientAdapter.IngredientClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: IngredientAdapter
    private var recipeDataHolder: RecipeDataHolder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* recipeDataHolder = if (activity is RecipeDataHolder) {
            activity as RecipeDataHolder?
        } else {
            throw RuntimeException(
                "Activity must implement WeatherDataHolder interface!"
            )
        }*/


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
            val items = MainActivity.database.ingredientDao().getAll()
            this.activity?.runOnUiThread {
                adapter.update(items)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        fab.setOnClickListener{

            fragmentManager?.let { it1 ->
                NewIngredientDialogFragment(this).show(
                    it1.beginTransaction(),
                    NewIngredientDialogFragment.TAG
                )
            }
        }

        initRecyclerView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayWeatherData()
    }
    private fun displayWeatherData() {

        tvMain.text = recipe.name
        /*tvDescription.text = weather?.description

        Glide.with(this)
            .load("https://openweathermap.org/img/w/${weather?.icon}.png")
            .transition(DrawableTransitionOptions().crossFade())
            .into(ivIcon)*/
    }

    override fun onIngredientCreated(newItem: Ingredient) {
        thread {
            val newId = MainActivity.database.ingredientDao().insert(newItem)
            val newIngredientItem = newItem.copy(
                id = newId
            )
            this.activity?.runOnUiThread {
                adapter.addItem(newIngredientItem)
            }
        }
    }

    override fun onItemChanged(item: Ingredient) {
        TODO("Not yet implemented")
    }

    override fun onItemDeleted(item: Ingredient) {
        TODO("Not yet implemented")
    }

    override fun onIngredientSelected(position: Int) {
        TODO("Not yet implemented")
    }
}