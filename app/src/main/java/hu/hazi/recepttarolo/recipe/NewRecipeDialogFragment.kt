package hu.hazi.recepttarolo.recipe

//import android.R
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import hu.hazi.recepttarolo.R
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class NewRecipeDialogFragment : DialogFragment() {
    interface NewRecipeDialogListener {
        fun onRecipeCreated(newItem: Recipe)
    }

    private lateinit var listener: NewRecipeDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewRecipeDialogListener
            ?: throw RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(hu.hazi.recepttarolo.R.string.new_recipe)
            .setView(getContentView())
            .setPositiveButton(hu.hazi.recepttarolo.R.string.ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onRecipeCreated(getShoppingItem())
                }
            }
            .setNegativeButton(hu.hazi.recepttarolo.R.string.cancel, null)
            .create()

    }

    private fun isValid() = nameEditText.text.isNotEmpty()

    private fun getShoppingItem() = Recipe(
        id = null,
        name = nameEditText.text.toString(),
        description = descriptionEditText.text.toString(),
        category = Recipe.Category.getByOrdinal(categorySpinner.selectedItemPosition)
            ?: Recipe.Category.SOUP
    )

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var estimatedPriceEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var alreadyPurchasedCheckBox: CheckBox

    private fun getContentView(): View {
        val contentView =
            LayoutInflater.from(context).inflate(hu.hazi.recepttarolo.R.layout.dialog_new_recipe, null)
        nameEditText = contentView.findViewById(hu.hazi.recepttarolo.R.id.RecipeNameEditText)
        descriptionEditText = contentView.findViewById(hu.hazi.recepttarolo.R.id.RecipeDescriptionEditText)
        categorySpinner = contentView.findViewById(hu.hazi.recepttarolo.R.id.RecipeCategorySpinner)
        categorySpinner.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.category_items)
            )
        )
        return contentView
    }

    companion object {
        const val TAG = "NewShoppingItemDialogFragment"
    }
}