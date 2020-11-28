package hu.hazi.recepttarolo.recipe.pager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.hazi.recepttarolo.MainActivity
import hu.hazi.recepttarolo.R
import hu.hazi.recepttarolo.recipe.Recipe
import hu.hazi.recepttarolo.recipe.RecipeDao
import hu.hazi.recepttarolo.recipe.RecipeDatabase
import kotlinx.android.synthetic.main.activity_recipe.*

class RecipeActivity : AppCompatActivity(), RecipeDataHolder {
    private var recipe: Recipe? = null
    private lateinit var database: RecipeDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        val recipePagerAdapter = RecipePagerAdapter(supportFragmentManager, this)
        mainViewPager.adapter = recipePagerAdapter
        var recipeId =  intent.getLongExtra("Recipe");
        recipe = intent.getLongExtra("Recipe");
MainActivity.database.recipeDao().getAll()
    }

    override fun getRecipe(): Recipe? {
        TODO("Not yet implemented")
    }
}