package hu.hazi.recepttarolo.recipe.pager

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.hazi.recepttarolo.R
import hu.hazi.recepttarolo.recipe.Recipe


class EditRecipeDialogFragment (var recipeFragment: RecipeFragment, var editRecipe: Recipe): DialogFragment() {
    interface EditRecipeDialogListener {
        fun onRecipeEdited(editedRecipe: Recipe)
    }

    private lateinit var listener: EditRecipeDialogFragment.EditRecipeDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = recipeFragment as? EditRecipeDialogFragment.EditRecipeDialogListener
            ?: throw RuntimeException("Activity must implement the EditRecipeDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.edit_recipe)
            .setView(getContentView())
            .setPositiveButton(hu.hazi.recepttarolo.R.string.ok) { dialogInterface, i ->
                if (isValid()) {
                        listener.onRecipeEdited(getEditedRecipe())
                }
            }
            .setNegativeButton(hu.hazi.recepttarolo.R.string.cancel, null)
            .create()

    }

    private fun isValid() = descriptionEditText.text.isNotEmpty()

    private fun getEditedRecipe(): Recipe {
        editRecipe.name= nameEditText.text.toString()
        editRecipe.description= descriptionEditText.text.toString()
        editRecipe.category = Recipe.Category.getByOrdinal(categorySpinner.selectedItemPosition)
            ?: Recipe.Category.SOUP
        return editRecipe
    }


    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var categorySpinner: Spinner


    private fun getContentView(): View {
        val contentView =
            LayoutInflater.from(context).inflate(hu.hazi.recepttarolo.R.layout.fragment_edit_recipe_dialog, null)
        nameEditText = contentView.findViewById(hu.hazi.recepttarolo.R.id.EditingRecipeNameEditText)
        descriptionEditText = contentView.findViewById(hu.hazi.recepttarolo.R.id.EditinRecipeDescriptionEditText)
        categorySpinner = contentView.findViewById(hu.hazi.recepttarolo.R.id.EditinRecipeCategorySpinner)
        categorySpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.category_items)
        )
        nameEditText.setText(editRecipe.name)
        descriptionEditText.setText(editRecipe.description)
        categorySpinner.setSelection(editRecipe.category.ordinal)

        return contentView
    }

    companion object {
        const val TAG = "NewRecipeDialogFragment"
    }
}