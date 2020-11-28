package hu.hazi.recepttarolo.recipe.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.hazi.recepttarolo.R
import kotlinx.android.synthetic.main.fragment_recipe.*


class RecipeFragment : Fragment() {
    private var recipeDataHolder: RecipeDataHolder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeDataHolder = if (activity is RecipeDataHolder) {
            activity as RecipeDataHolder?
        } else {
            throw RuntimeException(
                "Activity must implement WeatherDataHolder interface!"
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (recipeDataHolder?.getRecipe() != null) {
            displayWeatherData()
        }
    }
    private fun displayWeatherData() {
        val recipe = recipeDataHolder?.getRecipe()
        tvMain.text = recipe?.name
        /*tvDescription.text = weather?.description

        Glide.with(this)
            .load("https://openweathermap.org/img/w/${weather?.icon}.png")
            .transition(DrawableTransitionOptions().crossFade())
            .into(ivIcon)*/
    }
}