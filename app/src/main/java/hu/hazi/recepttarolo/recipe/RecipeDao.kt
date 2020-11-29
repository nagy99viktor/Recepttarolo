package hu.hazi.recepttarolo.recipe

import androidx.room.*

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAll(): List<Recipe>

    @Query("SELECT * FROM recipes WHERE id=:id ")
    fun getById(id: Long?): Recipe

    @Insert
    fun insert(shoppingItems: Recipe): Long

    @Update
    fun update(shoppingItem: Recipe)

    @Delete
    fun deleteItem(shoppingItem: Recipe)
}