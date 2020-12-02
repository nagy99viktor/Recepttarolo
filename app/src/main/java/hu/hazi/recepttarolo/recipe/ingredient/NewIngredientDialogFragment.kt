package hu.hazi.recepttarolo.recipe.ingredient


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

import hu.hazi.recepttarolo.recipe.ingredient.Ingredient
import hu.hazi.recepttarolo.recipe.pager.RecipeFragment


class NewIngredientDialogFragment (var recipeFragment: RecipeFragment): DialogFragment() {
    interface NewIngredientDialogListener {
        fun onIngredientCreated(newItem: Ingredient)
    }

    private lateinit var listener: NewIngredientDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = recipeFragment as? NewIngredientDialogListener
            ?: throw RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(hu.hazi.recepttarolo.R.string.new_ingredient)
            .setView(getContentView())
            .setPositiveButton(hu.hazi.recepttarolo.R.string.ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onIngredientCreated(getIngredientItem())
                }
            }
            .setNegativeButton(hu.hazi.recepttarolo.R.string.cancel, null)
            .create()

    }

    private fun isValid() = descriptionEditText.text.isNotEmpty()

    private fun getIngredientItem() = Ingredient(
        id = null,
        description = descriptionEditText.text.toString(),
        recipeId = null
    )




    private lateinit var descriptionEditText: EditText


    private fun getContentView(): View {
        val contentView =
            LayoutInflater.from(context).inflate(hu.hazi.recepttarolo.R.layout.fragment_new_ingredient_dialog, null)
        descriptionEditText = contentView.findViewById(hu.hazi.recepttarolo.R.id.IngredientDescriptionEditText)

        return contentView
    }

    companion object {
        const val TAG = "NewIngredientDialogFragment"
    }
}