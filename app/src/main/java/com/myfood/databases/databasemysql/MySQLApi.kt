package com.myfood.databases.databasemysql


import com.myfood.databases.databasemysql.entity.*
import retrofit2.Response
import retrofit2.http.*

interface MySQLApi {

    // Obtiene los atributos de un producto a partir de una url con su codigo de barras
    @GET
    suspend fun getOpenFoodProduct(@Url name: String): Response<OpenFoodEntity>

    // Obtiene si el usuario se ha logeado correctamente y el id de usuario
    @GET("/login/getUserId.php")
    suspend fun login(
        @Query("n") name: String,
        @Query("p") password: String
    ): Response<LoginEntity>

    // Inserta un nuevo usuario en la base de datos MySQL
    @GET("/signup/insertUser.php")
    suspend fun insertUser(
        @Query("n") name: String,
        @Query("s") surnames: String,
        @Query("e") email: String,
        @Query("p") password: String,
    ): Response<OneValueEntity>

    // Borra un usuario de la base de datos MySQL a partir de su id
    @GET("/signup/deleteUser.php")
    suspend fun deleteUser(
        @Query("id") id: String
    ): Response<SimpleResponseEntity>

    // Obtiene la lista de productos de despensa para un usuario concreto
    @GET("/pantry/getPantryList.php")
    suspend fun getPantryList(@Query("u") idUser: String): Response<PantryListEntity>

    // Borra un producto de despensa a partir de su id
    @GET("/pantry/deletePantry.php")
    suspend fun deletePantry(@Query("id") idPantry: String): Response<SimpleResponseEntity>

    // Petición POST que inserta un nuevo producto de despensa en la base de datos MySQL
    // a partir de un id de usuario
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

    // Petición POST que actualiza un producto de despensa en la base de datos MySQL
    // a partir de la id del producto
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

    // Obtiene los atributos de un producto de despensa de la base de datos MySQL
    @GET("/pantry/getPantryProduct.php")
    suspend fun getPantryProduct(@Query("id") idPantry: String):
            Response<PantryProductEntity>

    // Inserta un producto de compra en la base de datos MySQL
    @GET("/shop/insertShop.php")
    suspend fun insertShop(
        @Query("n") name: String,
        @Query("q") quantity: String,
        @Query("qu") quantityUnit: String,
        @Query("u") userId: String
    ): Response<OneValueEntity>

    // Actualiza un producto de compra en la base de datos MySQL a partir
    // de la id del producto
    @GET("/shop/updateShop.php")
    suspend fun updateShop(
        @Query("n") name: String,
        @Query("q") quantity: String,
        @Query("qu") quantityUnit: String,
        @Query("id") idShop: String
    ): Response<SimpleResponseEntity>

    // Obtiene la lista de productos de commpra de la base de datos MySQL
    // a partir de un id de usuario
    @GET("/shop/getShopList.php")
    suspend fun getShopList(@Query("u") userId: String): Response<ShopListEntity>

    // Elimina un producto de compra de la base de datos MySQL a partir de
    // de la id del producto
    @GET("/shop/deleteShop.php")
    suspend fun deleteShop(@Query("id") idShop: String): Response<SimpleResponseEntity>

    // Obtiene los atributos de un producto de compra de la base de datos MySQL
    // a partir de la id del producto
    @GET("/shop/getShopProduct.php")
    suspend fun getShopProduct(@Query("id") idShop: String): Response<ShopProductEntity>

    // Obtiene la lista de productos de despensa según su caducidad para un id de usuario
    // concreto de la base de datos MySQL
    @GET("/expiration/getExpirationList.php")
    suspend fun getExpirationList(
        @Query("e") expiration: String,
        @Query("u") idUser: String,
    ): Response<ExpirationListEntity>

    // Elimina todos los productos caducados para un id de usuario concreto en ka base de
    // datos MySQL
    @GET("/expiration/removeExpired.php")
    suspend fun removeExpired(@Query("u") idUser: String): Response<SimpleResponseEntity>

    // Obtiene la lista de recetas de la base de datos MySQL para un idioma concreto
    @GET("/recipe/getRecipeList.php")
    suspend fun getRecipeList(@Query("l") language: String): Response<RecipeListEntity>

    // Obtiene la lista de recetas sugeridas según los productos de despensa para un usuario
    // concreto y un idioma determinado en la base de datos MysQL
    @GET("/recipe/getRecipesSuggested.php")
    suspend fun getRecipesSuggested(
        @Query("l") language: String,
        @Query("u") user: String
    ): Response<RecipeListEntity>

    // Obtiene los atributos de una receta en un idioma dado el id de receta e idioma de la
    // base de datos MySQL
    @GET("/recipe/getRecipe.php")
    suspend fun getRecipe(
        @Query("r") idRecipe: String,
        @Query("l") language: String
    ): Response<RecipeEntity>

    // Cambia el email para un id de usuario concreto en la base de datos MySQL
    @GET("/config/changeEmail.php")
    suspend fun changeEmail(
        @Query("e") email: String,
        @Query("u") user: String
    ): Response<SimpleResponseEntity>

    // Obtiene el email de un id de usario concreto en la base de datos MySQL
    @GET("/config/getEmail.php")
    suspend fun getEmail(@Query("u") user: String): Response<OneValueEntity>

    // Cambio la contraseña para un id de usuario concreto en la base de datos MySQL
    @GET("/config/changePassword.php")
    suspend fun changePassword(
        @Query("p") password: String,
        @Query("u") user: String
    ): Response<SimpleResponseEntity>

    // Obtiene la contraseña de un id de usuario concreto en la base de datos MySQL
    @GET("/config/getPassword.php")
    suspend fun getPassword(
        @Query("u") user: String
    ): Response<OneValueEntity>

    // Si el correo existe envia un link a dicho correo del usuario para que resetee la
    // contraseña en el idioma de la aplicación
    @GET("/forgottenpassword/sendLink.php")
    suspend fun sendLink(
        @Query("l") language: String,
        @Query("e") email: String
    ): Response<SimpleResponseEntity>

    // Obtiene el grupo de tipo de nutrientes en un idioma concreto
    @GET("/pantry/getNutrients.php")
    suspend fun getNutrients(
        @Query("l") language: String
    ): Response<NutrientGroupEntity>

    // Obtiene los nutrientes de de un tipo de grupo de nutrientes para un id de alimento
    // y idioma concreto.
    @GET("/pantry/getNutrientsByType.php")
    suspend fun getNutrientsByType(
        @Query("t") typeNutrient: String,
        @Query("id") idFood: String,
        @Query("l") language: String
    ): Response<NutrientListTypeEntity>
}