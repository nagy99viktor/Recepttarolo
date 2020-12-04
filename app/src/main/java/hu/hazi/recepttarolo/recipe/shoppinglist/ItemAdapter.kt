package hu.hazi.recepttarolo.recipe.shoppinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import hu.hazi.recepttarolo.R

class ItemAdapter(private val listener: ItemClickListener) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val items = mutableListOf<Item>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.shopping_list, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.descriptionTextView.text = item.description
        holder.isBoughtCheckBox.isChecked = item.isBought

        holder.item = item
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: Item) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }


    fun update(shoppingItems: List<Item>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onItemChanged(item: Item)
        fun onItemDeleted(item: Item)
        fun onItemEdit(item: Item)
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val descriptionTextView: TextView
        val isBoughtCheckBox: CheckBox
        val removeButton: ImageButton
        val editButton: ImageButton

        var item: Item? = null

        init {

            descriptionTextView = itemView.findViewById(R.id.ItemDescriptionTextView)
            isBoughtCheckBox = itemView.findViewById(R.id.ItemIsBoughtCheckBox)
            removeButton = itemView.findViewById(R.id.ItemRemoveButton)
            editButton = itemView.findViewById(R.id.ItemEditButton)
            isBoughtCheckBox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                item?.let {
                    val newItem = it.copy(
                        isBought = isChecked
                    )
                    item = newItem
                    listener.onItemChanged(newItem)
                }
            })
            removeButton.setOnClickListener {
                item?.let { it1 -> listener.onItemDeleted(it1) }
            }
            editButton.setOnClickListener {
                item?.let { it1 -> listener.onItemEdit(it1) }
            }

        }
    }
}