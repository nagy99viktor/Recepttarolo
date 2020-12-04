package hu.hazi.recepttarolo.recipe.pager

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.hazi.recepttarolo.MainActivity
import hu.hazi.recepttarolo.R
import hu.hazi.recepttarolo.recipe.*
import hu.hazi.recepttarolo.recipe.ingredient.Ingredient
import hu.hazi.recepttarolo.recipe.ingredient.NewIngredientDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_recipe.*

import kotlin.concurrent.thread

class RecipeActivity : AppCompatActivity() {
   // private var recipeId =  intent.getLongExtra("Recipe", 0)
    val recipePagerAdapter: RecipePagerAdapter =  RecipePagerAdapter(supportFragmentManager, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        loadItemsInBackground()
        mainViewPager.adapter = recipePagerAdapter


    }



    private fun loadItemsInBackground() {
        thread {
            val items =  Database.getInstance(this).recipeDao().getAll()
            runOnUiThread {
                recipePagerAdapter.update(items)
                mainViewPager.currentItem = intent.getIntExtra("Recipe", 0);
            }
        }

    }

}


