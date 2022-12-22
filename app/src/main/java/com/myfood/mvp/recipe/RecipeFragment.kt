package com.myfood.mvp.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myfood.databases.databasemysql.entity.RecipeEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.databinding.RecipeFragmentBinding

class RecipeFragment(
    private val idRecipe: String,
    private val idLanguage: String,
) : Fragment(), RecipeContract.View {

    //Declaración de variables globales
    private var _binding: RecipeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recipeModel: RecipeModel
    private lateinit var recipePresenter: RecipePresenter
    private var mutableTranslations: MutableMap<String, Translation>? = null

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

        //Creamos el modelo
        recipeModel = RecipeModel()

        //Creamos el presentador
        recipePresenter = RecipePresenter(this, recipeModel, requireContext())

        //Obtenemos los atributos del producto de despensa
        val currentLanguage = recipePresenter.getCurrentLanguage()
        this.mutableTranslations = recipePresenter.getTranslations(currentLanguage.toInt())
        setTranslations()

        //Obtenemos los atributos de la receta
        recipePresenter.getRecipe(idRecipe, idLanguage).observe(this.viewLifecycleOwner)
        { recipe -> onRecipeLoaded(recipe) }
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
        binding.header.titleHeader.text =
            mutableTranslations?.get(com.myfood.constants.Constant.TITLE_RECIPE)!!.text
        binding.lRIPortions.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_PORTIONS)!!.text
        binding.lRIIngredients.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_INGREDIENTS)!!.text
        binding.lRIElaboration.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_DIRECTIONS)!!.text
    }
}