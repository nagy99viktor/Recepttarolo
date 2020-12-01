package hu.hazi.recepttarolo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import hu.hazi.recepttarolo.recipe.*

import hu.hazi.recepttarolo.recipe.pager.RecipeActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), RecipeAdapter.RecipeClickListener,
    NewRecipeDialogFragment.NewRecipeDialogListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    companion object Database{
        lateinit var database: RecipeDatabase
    }

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
        Database.database = Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java,
            "recipe"
        ).build()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        recyclerView = MainRecyclerView
        adapter = RecipeAdapter(this)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.recipeDao().getAll()
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
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemChanged(item: Recipe) {
        thread {
            database.recipeDao().update(item)
            Log.d("MainActivity", "ShoppingItem update was successful")
        }
    }

    override fun onItemDeleted(item: Recipe) {
        thread {
            database.recipeDao().deleteItem(item)
            Log.d("MainActivity", "ShoppingItem delete was successful")
        }
        loadItemsInBackground();
    }

    override fun onRecipeCreated(newItem: Recipe) {
        thread {
            val newId = database.recipeDao().insert(newItem)
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