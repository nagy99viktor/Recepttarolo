package hu.hazi.recepttarolo.recipe.pager

import android.content.Context

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import hu.hazi.recepttarolo.recipe.Recipe



class RecipePagerAdapter(fragmentManager: FragmentManager, private val context: Context) :
    FragmentPagerAdapter(fragmentManager) {
 private val items = mutableListOf<Recipe>()

    override fun getItem(position: Int): Fragment {
        return RecipeFragment(items[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return items[position].name;
    }

    override fun getCount(): Int = items.size

   fun update(recipeItems: List<Recipe>) {
       items.clear()
       items.addAll(recipeItems)
       notifyDataSetChanged()
   }


}