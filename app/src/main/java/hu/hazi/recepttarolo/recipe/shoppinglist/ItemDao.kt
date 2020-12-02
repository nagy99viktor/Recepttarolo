package hu.hazi.recepttarolo.recipe.shoppinglist

import androidx.room.*
import hu.hazi.recepttarolo.recipe.ingredient.Ingredient

@Dao
interface ItemDao {
    @Query("SELECT * FROM shoppingList")
    fun getAll(): List<Item>

    @Query("SELECT * FROM shoppingList WHERE id=:id ")
    fun getById(id: Long?): Item

    @Insert
    fun insert(items: Item): Long

    @Update
    fun update(item: Item)

    @Delete
    fun deleteItem(item: Item)
}