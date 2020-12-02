package hu.hazi.recepttarolo.recipe.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import hu.hazi.recepttarolo.R
import hu.hazi.recepttarolo.recipe.RecipeDatabase
import hu.hazi.recepttarolo.recipe.pager.RecipeActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.concurrent.thread

class ShoppingListActivity : AppCompatActivity(), ItemAdapter.ItemClickListener,
        NewItemDialogFragment.NewItemDialogListener {

        private lateinit var recyclerView: RecyclerView
        private lateinit var adapter: ItemAdapter
        private lateinit var database: RecipeDatabase
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            setSupportActionBar(toolbar)
            fab.setOnClickListener{
                NewItemDialogFragment().show(
                    supportFragmentManager,
                    NewItemDialogFragment.TAG
                )
            }

            initRecyclerView()

        }

        private fun initRecyclerView() {
            recyclerView = MainRecyclerView
            adapter = ItemAdapter(this)
            loadItemsInBackground()
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }

        private fun loadItemsInBackground() {
            thread {
                val items = database.itemDao().getAll()
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

        override fun onOptionsItemSelected(item: MenuItem) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            if(item.itemId == R.id.show_shopping_list){
                val showDetailsIntent = Intent()
                showDetailsIntent.setClass(this, ShoppingListActivity::class.java)
                startActivity(showDetailsIntent)
            }

        }

        override fun onItemChanged(item: Item) {
            thread {
                database.itemDao().update(item)
                Log.d("MainActivity", "ShoppingItem update was successful")
            }
        }

        override fun onItemDeleted(item: Item) {
            thread {
                database.itemDao().deleteItem(item)
                Log.d("MainActivity", "ShoppingItem delete was successful")
            }
            loadItemsInBackground();
        }

        override fun onItemCreated(newItem: Item) {
            thread {
                val newId = database.itemDao().insert(newItem)
                val newItem2 = newItem.copy(
                    id = newId,
                    isBought = true

                )
                runOnUiThread {
                    adapter.addItem(newItem2)
                }
            }
        }


    }