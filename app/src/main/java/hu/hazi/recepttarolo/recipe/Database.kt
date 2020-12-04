package hu.hazi.recepttarolo.recipe

import android.content.Context
import androidx.room.Room

object Database {

    private var database: RecipeDatabase? = null

    fun getInstance(context: Context): RecipeDatabase{

        val temp = database

        if(temp != null) {
            return temp
        }

        synchronized(this) {
            val newDatabase = Room.databaseBuilder(
                context,
                RecipeDatabase::class.java,
                "recipe"
            ).build()

            database = newDatabase

            return newDatabase
        }
    }
}