package hu.hazi.recepttarolo.recipe.ingredient

import androidx.room.*

@Dao
interface IngredientDao {
    @Query("SELECT * FROM ingredients")
    fun getAll(): List<Ingredient>

    @Query("SELECT * FROM ingredients WHERE id=:id ")
    fun getById(id: Long?): Ingredient

    @Insert
    fun insert(ingredients: Ingredient): Long

    @Update
    fun update(ingredient: Ingredient)

    @Delete
    fun deleteItem(ingredient: Ingredient)
}