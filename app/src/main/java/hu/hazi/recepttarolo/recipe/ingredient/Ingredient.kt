package hu.hazi.recepttarolo.recipe.ingredient

import androidx.room.*

import androidx.room.util.TableInfo
import hu.hazi.recepttarolo.recipe.Recipe


@Entity(foreignKeys = [ForeignKey(entity = Recipe::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("recipe-id"),
    onDelete = ForeignKey.NO_ACTION)],
    tableName = "ingredients"
)
data class Ingredient (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "recipe-id") val recipeId: Long
){}