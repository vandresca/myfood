package com.example.myfood.mvvm.data.network


import com.example.myfood.mvvm.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface MySQLApi {

    @GET
    suspend fun getOpenFoodProduct(@Url name: String): Response<OpenFoodEntity>

    @GET("/login/getUserId.php")
    suspend fun login(
        @Query("n") name: String,
        @Query("p") password: String
    ): Response<LoginEntity>

    @GET("/signup/insertUser.php")
    suspend fun insertUser(
        @Query("n") name: String,
        @Query("s") surnames: String,
        @Query("e") email: String,
        @Query("p") password: String,
    ): Response<SimpleResponseEntity>

    @GET("/pantry/getPantryList.php")
    suspend fun getPantryList(@Query("u") idUser: String): Response<PantryListEntity>

    @GET("/pantry/deletePantryProduct.php")
    suspend fun deletePantry(@Query("id") idPantry: String)

    @GET("/pantry/insertPantry.php")
    suspend fun insertPantry(
        @Query("cb") barcode: String,
        @Query("n") name: String,
        @Query("q") quantity: String,
        @Query("qu") quantityUnit: String,
        @Query("p") place: String,
        @Query("w") weight: String,
        @Query("pr") price: String,
        @Query("ed") expirationDate: String,
        @Query("pd") preferenceDate: String,
        @Query("i") image: String,
        @Query("b") brand: String,
        @Query("u") idUser: String
    )

    @GET("/pantry/updatePantry.php")
    suspend fun updatePantry(
        @Query("cb") barcode: String,
        @Query("n") name: String,
        @Query("q") quantity: String,
        @Query("qu") quantityUnit: String,
        @Query("p") place: String,
        @Query("w") weight: String,
        @Query("pr") price: String,
        @Query("ed") expirationDate: String,
        @Query("pd") preferenceDate: String,
        @Query("i") image: String,
        @Query("b") brand: String,
        @Query("id") idPantry: String
    )

    @GET("/pantry/getPantryProduct.php")
    suspend fun getPantryProduct(@Query("id") idPantry: String):
            Response<PantryProductEntity>

    @GET("/shop/insertShop.php")
    suspend fun insertShop(
        @Query("n") name: String,
        @Query("q") quantity: String,
        @Query("qu") quantityUnit: String,
        @Query("u") userId: String
    )

    @GET("/shop/updateShop.php")
    suspend fun updateShop(
        @Query("n") name: String,
        @Query("q") quantity: String,
        @Query("qu") quantityUnit: String,
        @Query("id") idShop: String
    )

    @GET("/shop/getShopList.php")
    suspend fun getShopList(@Query("u") userId: String): Response<ShopListEntity>

    @GET("/shop/deleteShop.php")
    suspend fun deleteShop(@Query("id") idShop: String)

    @GET("/shop/getShopProduct.php")
    suspend fun getShopProduct(@Query("id") idShop: String): Response<ShopProductEntity>

    @GET("/expiration/getExpirationList.php")
    suspend fun getExpirationList(
        @Query("e") expiration: String,
        @Query("u") idUser: String,
    ): Response<ExpirationListEntity>

    @GET("/expiration/removeExpired.php")
    suspend fun removeExpired(@Query("u") idUser: String): Response<SimpleResponseEntity>

    @GET("/recipe/getRecipeList.php")
    suspend fun getRecipeList(@Query("l") language: String): Response<RecipeListEntity>

    @GET("/recipe/getRecipesSuggested.php")
    suspend fun getRecipesSuggested(@Query("l") language: String): Response<RecipeListEntity>

    @GET("/recipe/getRecipe.php")
    suspend fun getRecipe(
        @Query("r") idRecipe: String,
        @Query("l") language: String
    ): Response<RecipeEntity>

    @GET("/config/changeEmail.php")
    suspend fun changeEmail(
        @Query("e") email: String,
        @Query("u") user: String
    ): Response<SimpleResponseEntity>

    @GET("/config/getEmail.php")
    suspend fun getEmail(@Query("u") user: String): Response<OneValueEntity>

    @GET("/config/changePassword.php")
    suspend fun changePassword(
        @Query("p") password: String,
        @Query("u") user: String
    ): Response<SimpleResponseEntity>

    @GET("/config/getPassword.php")
    suspend fun getPassword(
        @Query("u") user: String
    ): Response<OneValueEntity>

    @GET("/forgottenpassword/sendLink.php")
    suspend fun sendLink(
        @Query("l") language: String,
        @Query("e") email: String
    ): Response<SimpleResponseEntity>

    @GET("/pantry/getNutrients.php")
    suspend fun getNutrients(): Response<NutrientGroupEntity>

    @GET("/pantry/getNutrientsByType.php")
    suspend fun getNutrientsByType(
        @Query("t") typeNutrient: String,
        @Query("id") idFood: String
    ): Response<NutrientListTypeEntity>
}