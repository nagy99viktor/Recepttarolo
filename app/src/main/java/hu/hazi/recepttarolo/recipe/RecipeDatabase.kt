package hu.hazi.recepttarolo.recipe

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.hazi.recepttarolo.recipe.ingredient.Ingredient
import hu.hazi.recepttarolo.recipe.ingredient.IngredientDao
import hu.hazi.recepttarolo.recipe.shoppinglist.Item
import hu.hazi.recepttarolo.recipe.shoppinglist.ItemDao

@Database(entities = [Recipe::class, Ingredient::class, Item::class], version = 4)
@TypeConverters(value = [Recipe.Category::class])
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    abstract fun ingredientDao(): IngredientDao
    abstract fun itemDao(): ItemDao
}