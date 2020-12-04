package hu.hazi.recepttarolo.recipe.ingredient


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.hazi.recepttarolo.R
import hu.hazi.recepttarolo.recipe.pager.RecipeFragment


class NewIngredientDialogFragment (var recipeFragment: RecipeFragment, var editIngredient: Ingredient?): DialogFragment() {
    interface NewIngredientDialogListener {
        fun onIngredientCreated(newItem: Ingredient)
        fun onIngredientEdited(newItem: Ingredient)
    }

    private lateinit var listener: NewIngredientDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = recipeFragment as? NewIngredientDialogListener
            ?: throw RuntimeException("Activity must implement the NewIngredientDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = when(editIngredient){
            null -> getString(R.string.new_ingredient)
            else -> getString(R.string.edit_ingredient)
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setView(getContentView())
            .setPositiveButton(hu.hazi.recepttarolo.R.string.ok) { dialogInterface, i ->
                if (isValid()) {
                    if(editIngredient == null) {
                        listener.onIngredientCreated(getIngredientItem())
                    }else{
                        listener.onIngredientEdited(getEditedIngredient()!!)
                    }
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

    private fun getEditedIngredient(): Ingredient? {
        editIngredient?.description= descriptionEditText.text.toString()
        return editIngredient
    }

    private lateinit var descriptionEditText: EditText

    private fun getContentView(): View {
        val contentView =
            LayoutInflater.from(context).inflate(hu.hazi.recepttarolo.R.layout.fragment_new_ingredient_dialog, null)
        descriptionEditText = contentView.findViewById(hu.hazi.recepttarolo.R.id.IngredientDescriptionEditText)
        if(editIngredient!=null) {
            descriptionEditText.setText(editIngredient?.description)
        }
        return contentView
    }

    companion object {
        const val TAG = "NewIngredientDialogFragment"
    }
}