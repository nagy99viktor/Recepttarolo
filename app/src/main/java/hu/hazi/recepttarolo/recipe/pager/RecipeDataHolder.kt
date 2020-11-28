package hu.hazi.recepttarolo.recipe.pager

import hu.hazi.recepttarolo.recipe.Recipe

interface RecipeDataHolder {
    fun getRecipe(): Recipe?
}