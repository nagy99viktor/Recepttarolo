package hu.hazi.recepttarolo.recipe.ingredient

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import hu.hazi.recepttarolo.recipe.Recipe


@Entity(foreignKeys = [ForeignKey(entity = Recipe::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("recipe_id"),
    onDelete = ForeignKey.NO_ACTION)],
    tableName = "ingredients"
)
data class Ingredient (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "recipe_id") var recipeId: Long?
){}