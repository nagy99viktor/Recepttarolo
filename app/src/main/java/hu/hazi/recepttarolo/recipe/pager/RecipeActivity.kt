package hu.hazi.recepttarolo.recipe.pager

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.hazi.recepttarolo.MainActivity
import hu.hazi.recepttarolo.R
import hu.hazi.recepttarolo.recipe.Recipe
import hu.hazi.recepttarolo.recipe.RecipeDao
import hu.hazi.recepttarolo.recipe.RecipeDatabase
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlin.concurrent.thread

class RecipeActivity : AppCompatActivity() {
    private var recipeId: Long? = null
    val recipePagerAdapter: RecipePagerAdapter =  RecipePagerAdapter(supportFragmentManager, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        loadItemsInBackground()
        mainViewPager.adapter = recipePagerAdapter
        recipeId =  intent.getLongExtra("Recipe", 0)

    }



    private fun loadItemsInBackground() {
        thread {
            val items = MainActivity.database.recipeDao().getAll()
            runOnUiThread {
                recipePagerAdapter.update(items)
            }
        }

    }
/*
    private class GetAllContactsAsyncTask :
        AsyncTask<Void?, Void?, Void?>() {


        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)

            val recipePagerAdapter = RecipePagerAdapter(supportFragmentManager, this)
            mainViewPager.adapter = recipePagerAdapter
        }

        override fun doInBackground(vararg params: Void?): List<Recipe>? {
            return MainActivity.database.recipeDao().getAll()
        }
    }*/
}


