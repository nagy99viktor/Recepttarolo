package hu.hazi.recepttarolo.recipe.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.hazi.recepttarolo.R
import hu.hazi.recepttarolo.recipe.Database
import hu.hazi.recepttarolo.recipe.RecipeDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_shopping_list.*

import kotlin.concurrent.thread

class ShoppingListActivity : AppCompatActivity(), ItemAdapter.ItemClickListener,
        NewItemDialogFragment.NewItemDialogListener {

        private lateinit var recyclerView: RecyclerView
        private lateinit var adapter: ItemAdapter
        private lateinit var database: RecipeDatabase
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_shopping_list)
            newItemFab.setOnClickListener{
                NewItemDialogFragment().show(
                    supportFragmentManager,
                    NewItemDialogFragment.TAG
                )
            }


        }

    override fun onResume() {
        super.onResume()
        database= Database.getInstance(this)

        initRecyclerView()
    }

        private fun initRecyclerView() {
            recyclerView = ItemRecyclerView
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
            menuInflater.inflate(R.menu.shopping_list_menu, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            if(item.itemId == R.id.delete_shopping_list){
                thread {
                    Database.getInstance(this).itemDao().deleteAll()
                    loadItemsInBackground();
                }

                return true;
            }
            return super.onOptionsItemSelected(item)

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
                    id = newId
                )
                runOnUiThread {
                    adapter.addItem(newItem2)
                }
            }
        }


    }