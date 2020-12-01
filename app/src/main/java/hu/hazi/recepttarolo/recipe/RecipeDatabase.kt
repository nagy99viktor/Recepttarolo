package hu.hazi.recepttarolo.recipe

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.hazi.recepttarolo.recipe.ingredient.Ingredient
import hu.hazi.recepttarolo.recipe.ingredient.IngredientDao

@Database(entities = [Recipe::class, Ingredient::class], version = 3)
@TypeConverters(value = [Recipe.Category::class])
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    abstract fun ingredientDao(): IngredientDao
}