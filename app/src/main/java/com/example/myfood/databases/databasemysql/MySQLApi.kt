package com.example.myfood.mvvm.data.network


import com.example.myfood.mvvm.data.model.*
import retrofit2.Response
import retrofit2.http.*

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
    ): Response<OneValueEntity>

    @GET("/signup/deleteUser.php")
    suspend fun deleteUser(
        @Query("id") id: String
    ): Response<SimpleResponseEntity>

    @GET("/pantry/getPantryList.php")
    suspend fun getPantryList(@Query("u") idUser: String): Response<PantryListEntity>

    @GET("/pantry/deletePantry.php")
    suspend fun deletePantry(@Query("id") idPantry: String): Response<SimpleResponseEntity>

    @FormUrlEncoded
    @POST("/pantry/insertPantry.php")
    suspend fun insertPantry(
        @Field("cb") barcode: String,
        @Field("n") name: String,
        @Field("q") quantity: String,
        @Field("qu") quantityUnit: String,
        @Field("p") place: String,
        @Field("w") weight: String,
        @Field("pr") price: String,
        @Field("ed") expirationDate: String,
        @Field("pd") preferenceDate: String,
        @Field("i") image: String,
        @Field("b") brand: String,
        @Field("u") idUser: String
    ): Response<OneValueEntity>

    @FormUrlEncoded
    @POST("/pantry/updatePantry.php")
    suspend fun updatePantry(
        @Field("cb") barcode: String,
        @Field("n") name: String,
        @Field("q") quantity: String,
        @Field("qu") quantityUnit: String,
        @Field("p") place: String,
        @Field("w") weight: String,
        @Field("pr") price: String,
        @Field("ed") expirationDate: String,
        @Field("pd") preferenceDate: String,
        @Field("i") image: String,
        @Field("b") brand: String,
        @Field("id") idPantry: String
    ): Response<SimpleResponseEntity>

    @GET("/pantry/getPantryProduct.php")
    suspend fun getPantryProduct(@Query("id") idPantry: String):
            Response<PantryProductEntity>

    @GET("/shop/insertShop.php")
    suspend fun insertShop(
        @Query("n") name: String,
        @Query("q") quantity: String,
        @Query("qu") quantityUnit: String,
        @Query("u") userId: String
    ): Response<OneValueEntity>

    @GET("/shop/updateShop.php")
    suspend fun updateShop(
        @Query("n") name: String,
        @Query("q") quantity: String,
        @Query("qu") quantityUnit: String,
        @Query("id") idShop: String
    ): Response<SimpleResponseEntity>

    @GET("/shop/getShopList.php")
    suspend fun getShopList(@Query("u") userId: String): Response<ShopListEntity>

    @GET("/shop/deleteShop.php")
    suspend fun deleteShop(@Query("id") idShop: String): Response<SimpleResponseEntity>

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
    suspend fun getRecipesSuggested(
        @Query("l") language: String,
        @Query("u") user: String
    ): Response<RecipeListEntity>

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
    suspend fun getNutrients(
        @Query("l") language: String
    ): Response<NutrientGroupEntity>

    @GET("/pantry/getNutrientsByType.php")
    suspend fun getNutrientsByType(
        @Query("t") typeNutrient: String,
        @Query("id") idFood: String
    ): Response<NutrientListTypeEntity>
}