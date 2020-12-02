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


class NewItemDialogFragment : DialogFragment() {
    interface NewItemDialogListener {
        fun onItemCreated(newItem: Item)
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
                    listener.onItemCreated(getShoppingItem())
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private fun isValid() = nameEditText.text.isNotEmpty()

    private fun getShoppingItem() = Item(
        id = null,
        description = descriptionEditText.text.toString(),
        isBought = alreadyPurchasedCheckBox.isChecked
    )

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var estimatedPriceEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var alreadyPurchasedCheckBox: CheckBox

    private fun getContentView(): View {
        val contentView =
            LayoutInflater.from(context).inflate(R.layout.fragment_new_item_dialog, null)
        descriptionEditText = contentView.findViewById(R.id.ItemDescriptionEditText)
        return contentView
    }

    companion object {
        const val TAG = "NewShoppingItemDialogFragment"
    }
}