package com.myfood.mvp.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myfood.constants.Constant
import com.myfood.databases.databasemysql.entity.RecipeEntity
import com.myfood.databinding.RecipeFragmentBinding

class RecipeFragment(
    private val idRecipe: String,
    private val idLanguage: String,
) : Fragment(), RecipeContract.View {

    //Declaración de variables globales
    private var _binding: RecipeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recipePresenter: RecipePresenter
    private var mutableTranslations: MutableMap<String, String> = mutableMapOf()

    //Método onCreateView
    //Mientras se está creando la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Unidades de cantidad
        _binding = RecipeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método onViewCreated
    //Cuando la vista está creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutRecipe.visibility = View.INVISIBLE

        //Creamos el presentador
        recipePresenter = RecipePresenter(this, requireContext())

        //Obtenemos el idioma de la App y establecemos las traducciones
        mutableTranslations = recipePresenter.getTranslationsScreen()
        setTranslations()

        //Obtenemos los atributos de la receta
        recipePresenter.getRecipe(idRecipe, idLanguage)
    }

    //Metodo que se ejecuta una vez obtenemos los atributos de la receta
    override fun onRecipeLoaded(recipeEntity: RecipeEntity) {

        //Comprobamos que la respuesta es correcta
        if (recipeEntity.status == com.myfood.constants.Constant.OK) {

            //Asignamos los valore a los campos de texto
            binding.tvRIName.text = recipeEntity.title
            binding.tvRIPortions.text = recipeEntity.portions
            binding.tvRIngredients.text = recipeEntity.ingredients
            binding.tvRIElaboration.text = recipeEntity.directions

            //Hacemos visible otra vez el layout
            binding.layoutRecipe.visibility = View.VISIBLE
        }
    }

    //Establecemos las traducciones
    override fun setTranslations() {
        binding.header.titleHeader.text = mutableTranslations[Constant.TITLE_RECIPE]!!
        binding.lRIPortions.text = mutableTranslations[Constant.LABEL_PORTIONS]!!
        binding.lRIIngredients.text = mutableTranslations[Constant.LABEL_INGREDIENTS]!!
        binding.lRIElaboration.text = mutableTranslations[Constant.LABEL_DIRECTIONS]!!
    }
}