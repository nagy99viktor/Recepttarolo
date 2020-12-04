package hu.hazi.recepttarolo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.hazi.recepttarolo.recipe.*
import hu.hazi.recepttarolo.recipe.pager.RecipeActivity
import hu.hazi.recepttarolo.recipe.shoppinglist.ShoppingListActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(), RecipeAdapter.RecipeClickListener,
    NewRecipeDialogFragment.NewRecipeDialogListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    //private lateinit var filterSpinner: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener{
            NewRecipeDialogFragment().show(
                supportFragmentManager,
                NewRecipeDialogFragment.TAG
            )
        }


        filterSpinner.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.filters)
            )
        )
        filterSpinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                loadItemsInBackground()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                loadItemsInBackground()
            }

        })


    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        recyclerView = RecipeRecyclerView
        adapter = RecipeAdapter(this)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadItemsInBackground() {
        thread {
            val items = when (filterSpinner.selectedItemPosition) {
                0 -> Database.getInstance(this).recipeDao().getAll()
                1 -> Database.getInstance(this).recipeDao().getByCategory(Recipe.Category.SOUP)
                2 -> Database.getInstance(this).recipeDao()
                    .getByCategory(Recipe.Category.MAIN_COURSE)
                3 -> Database.getInstance(this).recipeDao().getByCategory(Recipe.Category.DESSERT)
                else -> Database.getInstance(this).recipeDao().getAll()
            }
            runOnUiThread {
                adapter.update(items)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(item.itemId == R.id.show_shopping_list){
            val showDetailsIntent = Intent()
            showDetailsIntent.setClass(this, ShoppingListActivity::class.java)
            startActivity(showDetailsIntent)
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemChanged(item: Recipe) {
        thread {
            Database.getInstance(this).recipeDao().update(item)
            Log.d("MainActivity", "ShoppingItem update was successful")
        }
    }

    override fun onItemDeleted(item: Recipe) {
        thread {
            Database.getInstance(this).ingredientDao().deleteByRecipeId(item.id)
            Database.getInstance(this).recipeDao().deleteItem(item)
            Log.d("MainActivity", "ShoppingItem delete was successful")
            loadItemsInBackground();
        }

    }

    override fun onRecipeCreated(newItem: Recipe) {
        thread {
            val newId = Database.getInstance(this).recipeDao().insert(newItem)
            val newShoppingItem = newItem.copy(
                id = newId
            )
            runOnUiThread {
                adapter.addItem(newShoppingItem)
            }
        }
    }
    override fun onRecipeSelected(position: Int) {
        val showDetailsIntent = Intent()
        showDetailsIntent.setClass(this, RecipeActivity::class.java)
        showDetailsIntent.putExtra("Recipe", position)
        startActivity(showDetailsIntent)
    }

}