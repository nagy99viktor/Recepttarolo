package hu.hazi.recepttarolo.recipe.pager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.hazi.recepttarolo.R
import hu.hazi.recepttarolo.recipe.Database
import hu.hazi.recepttarolo.recipe.Recipe
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlin.concurrent.thread

class RecipeActivity : AppCompatActivity() {
    val recipePagerAdapter: RecipePagerAdapter =  RecipePagerAdapter(supportFragmentManager, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        loadItemsInBackground()
        mainViewPager.adapter = recipePagerAdapter
    }

    private fun loadItemsInBackground() {
        thread {

            val items = when (intent.getIntExtra("filter", 0)) {
                0 -> Database.getInstance(this).recipeDao().getAll()
                1 -> Database.getInstance(this).recipeDao().getByCategory(Recipe.Category.SOUP)
                2 -> Database.getInstance(this).recipeDao()
                    .getByCategory(Recipe.Category.MAIN_COURSE)
                3 -> Database.getInstance(this).recipeDao().getByCategory(Recipe.Category.DESSERT)
                else -> Database.getInstance(this).recipeDao().getAll()
            }
            runOnUiThread {
                recipePagerAdapter.update(items)
                mainViewPager.currentItem = intent.getIntExtra("Recipe", 0);
            }
        }

    }

}


