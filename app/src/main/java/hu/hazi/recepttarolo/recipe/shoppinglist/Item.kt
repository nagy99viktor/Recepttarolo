package hu.hazi.recepttarolo.recipe.shoppinglist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoppingList")
data class Item (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "is_bought") val isBought: Boolean
){}