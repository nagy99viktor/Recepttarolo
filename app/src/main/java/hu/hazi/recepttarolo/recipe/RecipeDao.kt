package hu.hazi.recepttarolo.recipe

import androidx.room.*

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAll(): List<Recipe>

    @Query("SELECT * FROM recipes WHERE id=:id ")
    fun getById(id: Long?): Recipe

    @Query("SELECT * FROM recipes WHERE category=:category ")
    fun getByCategory(category: Recipe.Category): List<Recipe>

    @Insert
    fun insert(recipes: Recipe): Long

    @Update
    fun update(recipe: Recipe)

    @Delete
    fun deleteItem(recipe: Recipe)
}