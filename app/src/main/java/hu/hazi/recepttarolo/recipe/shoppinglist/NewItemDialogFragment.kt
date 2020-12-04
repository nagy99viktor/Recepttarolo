package hu.hazi.recepttarolo.recipe.shoppinglist

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

import hu.hazi.recepttarolo.R


class NewItemDialogFragment(var editItem: Item?) : DialogFragment() {
    interface NewItemDialogListener {
        fun onItemCreated(newItem: Item)
        fun onItemEdited(newItem: Item)
    }

    private lateinit var listener: NewItemDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new__item)
            .setView(getContentView())
            .setPositiveButton(R.string.ok) { dialogInterface, i ->
                if (isValid()) {
                    if(editItem==null) {
                        listener.onItemCreated(getItem())
                    }else{
                        listener.onItemEdited(getEditedItem()!!)
                    }
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private fun isValid() = descriptionEditText.text.isNotEmpty()


    private fun getItem() = Item(
        id = null,
        description = descriptionEditText.text.toString(),
        isBought = false
    )

    private fun getEditedItem(): Item? {
        editItem?.description= descriptionEditText.text.toString()
        return editItem
    }




    private lateinit var descriptionEditText: EditText



    private fun getContentView(): View {
        val contentView =
            LayoutInflater.from(context).inflate(R.layout.fragment_new_item_dialog, null)
        descriptionEditText = contentView.findViewById(R.id.ItemDescriptionEditText)
        if(editItem!=null) {
            descriptionEditText.setText(editItem?.description)
        }
        return contentView
    }

    companion object {
        const val TAG = "NewShoppingItemDialogFragment"
    }
}