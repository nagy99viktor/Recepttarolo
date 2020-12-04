package hu.hazi.recepttarolo.recipe.shoppinglist

import androidx.room.*

@Dao
interface ItemDao {
    @Query("SELECT * FROM shoppingList")
    fun getAll(): List<Item>

    @Query("SELECT * FROM shoppingList WHERE id=:id ")
    fun getById(id: Long?): Item

    @Insert
    fun insert(item: Item): Long

    @Insert
    fun insertAll(items: List<Item>)

    @Update
    fun update(item: Item)

    @Delete
    fun deleteItem(item: Item)

    @Query("DELETE FROM shoppingList")
    fun deleteAll()

}