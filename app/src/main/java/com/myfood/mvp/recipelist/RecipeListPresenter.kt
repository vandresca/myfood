package com.myfood.mvp.recipelist

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import com.myfood.databases.databasemysql.entity.RecipeListEntity

class RecipeListPresenter(
    private val recipeListFragment: RecipeListFragment,
    context: Context
) : RecipeListContract.Presenter {

    //Declaramos las variables globales
    private val recipeListModel: RecipeListModel = RecipeListModel()
    private lateinit var recipeAdapter: RecipeListAdapter
    private var recipeMutableList: MutableList<RecipeList> = mutableListOf()
    private val currentLanguage:String
    private val userId:String

    init {

        //Creamos las instancias de la base de datos
        recipeListModel.createInstances(context)

        //Obtenemos el idioma actual de la App
        currentLanguage = recipeListModel.getCurrentLanguage()

        //Obtenemos el id de usario acutal de la App
        userId = recipeListModel.getUserId()

        //Cargamos la lista de todas las recetas en el idioma actual
        recipeListModel.getRecipeList(currentLanguage)
            .observe(recipeListFragment) { data -> loadRecipes(data) }
    }


    //Metodo que se ejecuta una vez que tenemo la lista de redetas
    override fun loadRecipes(result: RecipeListEntity) {

        //Verificamos que la respuesta es correcta
        if (result.status == com.myfood.constants.Constant.OK) {

            //Tranformamos el objeto RecipListEntity a una Lista de objetos
            //RecipeList
            recipeMutableList = result.toMVP().toMutableList()

            //Creamos el adapter para el recyclerview
            recipeAdapter = RecipeListAdapter(
                recipeList = recipeMutableList,
                onClickListener =
                { recipeItem ->
                    recipeListFragment.onItemSelected(recipeItem, currentLanguage)
                }
            )

            //Incializamos el recyclerview
            Handler(Looper.getMainLooper()).post {
                recipeListFragment.initRecyclerView(recipeAdapter)
            }
        }
    }

    //Metodo que devuelve las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val translations = recipeListModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    //Metodo que filtra todas las recetas del sistema
    override fun filterAll() {
        recipeListModel.getRecipeList(currentLanguage)
            .observe(recipeListFragment) { data -> loadRecipes(data) }
    }

    //Metodo que filtra aquellas recetas que coinciden con los productos de despensa
    //del usuario
    override fun filterSuggested() {
        recipeListModel.getRecipesSuggested(currentLanguage, userId)
            .observe(recipeListFragment) { data -> loadRecipes(data) }
    }

    //Metodo que se ejecuta cuando se varia el contenido del campo de texto del buscador
    override fun doFilter(userFilter: Editable?) {

        //Almacenamos los objetos filtrados en una lista
        val recipeFiltered = recipeMutableList.filter { recipe ->
            recipe.title.lowercase().contains(userFilter.toString().lowercase())
        }

        //Actualizamos el recyclerview con los cambios
        recipeAdapter.updateExpirationList(recipeFiltered)
    }
}
