package hu.hazi.recepttarolo.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.hazi.recepttarolo.R


class RecipeAdapter(private val listener: RecipeClickListener) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    private lateinit var itemView: View
    private val items = mutableListOf<Recipe>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        itemView= LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recipe_list, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        holder.categoryTextView.text = itemView.context.resources.getStringArray(R.array.category_items)[item.category.ordinal]
        holder.item = item
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: Recipe) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }


    fun update(recipeItems: List<Recipe>) {
        items.clear()
        items.addAll(recipeItems)
        notifyDataSetChanged()
    }

    interface RecipeClickListener {
        fun onItemChanged(item: Recipe)
        fun onItemDeleted(item: Recipe)
        fun onRecipeSelected(position: Int)
    }



    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameTextView: TextView
        val categoryTextView: TextView
        val removeButton: ImageButton
        var item: Recipe? = null

        init {

            nameTextView = itemView.findViewById(R.id.nameTextView)
            categoryTextView = itemView.findViewById(R.id.categoryTextView)
            removeButton = itemView.findViewById(R.id.recipeRemoveButton)

            itemView.setOnClickListener { listener.onRecipeSelected(this.adapterPosition) }
            removeButton.setOnClickListener {
                item?.let { it1 -> listener.onItemDeleted(it1) }
            }

        }
    }
}