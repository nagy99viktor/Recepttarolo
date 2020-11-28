package hu.hazi.recepttarolo.recipe.pager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hu.hazi.recepttarolo.R
import hu.hazi.recepttarolo.recipe.RecipeDatabase

class RecipePagerAdapter(fragmentManager: FragmentManager, private val context: Context) :
    FragmentPagerAdapter(fragmentManager) {
    private lateinit var database: RecipeDatabase
    val items = database.recipeDao().getAll()
    override fun getItem(position: Int): Fragment {
        return RecipeFragment( )
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return items.get(position).name;
    }

    override fun getCount(): Int = items.size

}