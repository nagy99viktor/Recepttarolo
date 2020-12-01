package hu.hazi.recepttarolo.recipe.ingredient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.hazi.recepttarolo.R
import hu.hazi.recepttarolo.recipe.Recipe
import hu.hazi.recepttarolo.recipe.RecipeAdapter


class IngredientAdapter(private val listener: IngredientClickListener) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    private val items = mutableListOf<Ingredient>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.ingredient_list, parent, false)
        return IngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val item = items[position]
        holder.descriptionTextView.text = item.description
        holder.item = item
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: Ingredient) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }


    fun update(ingredientItems: List<Ingredient>) {
        items.clear()
        items.addAll(ingredientItems)
        notifyDataSetChanged()
    }

    interface IngredientClickListener {
        fun onItemChanged(item: Ingredient)
        fun onItemDeleted(item: Ingredient)
        fun onIngredientSelected(position: Int)
    }



    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val descriptionTextView: TextView
        val removeButton: ImageButton
        var item: Ingredient? = null

        init {

            descriptionTextView = itemView.findViewById(R.id.IngredientDescriptionTextView)
            removeButton = itemView.findViewById(R.id.IngredientRemoveButton)

            itemView.setOnClickListener { listener?.onIngredientSelected(this.adapterPosition) }
            removeButton.setOnClickListener {
                item?.let { it1 -> listener.onItemDeleted(it1) }
            }

        }
    }
}