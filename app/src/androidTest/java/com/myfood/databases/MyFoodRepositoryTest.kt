package com.myfood.databases


import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.myfood.enum.ScreenType
import com.myfood.getOrAwaitValueTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

internal class MyFoodRepositoryTest {
    private lateinit var context: Context
    private lateinit var myFoodRepository: com.myfood.databases.MyFoodRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        myFoodRepository = com.myfood.databases.MyFoodRepository()
        myFoodRepository.getInstance(context)
    }

    @Test
    fun getCurrentLanguage__updateCurrentLanguage() {
        val initValue = myFoodRepository.getCurrentLanguage()
        myFoodRepository.updateCurrentLanguage("2")
        val modifiedValue = myFoodRepository.getCurrentLanguage()
        Assert.assertEquals(modifiedValue, "2")
        myFoodRepository.updateCurrentLanguage(initValue)
        val modifiedValue2 = myFoodRepository.getCurrentLanguage()
        Assert.assertEquals(modifiedValue2, initValue)
    }

    @Test
    fun getTranslations() {
        val response = myFoodRepository.getTranslations(5, ScreenType.LOGIN.int)
        Assert.assertEquals(response.size, 6)

        Assert.assertEquals(response[0].word, "name")
        Assert.assertEquals(response[0].text, "Nom")

        Assert.assertEquals(response[1].word, "password")
        Assert.assertEquals(response[1].text, "Contrasenya")

        Assert.assertEquals(response[2].word, "login")
        Assert.assertEquals(response[2].text, "Login")

        Assert.assertEquals(response[3].word, "signUp")
        Assert.assertEquals(response[3].text, "Inscriure")

        Assert.assertEquals(response[4].word, "forgotPass")
        Assert.assertEquals(response[4].text, "Has oblidat la contrasenya")

        Assert.assertEquals(response[5].word, "loginIncorrect")
        Assert.assertEquals(response[5].text, "Nom d' usuari o contrasenya incorrecta")
    }

    @Test
    fun getQuantitiesUnit__updateQuantityUnit__addQuantityUnit__deleteQuantityUnit() {
        val initSize = myFoodRepository.getQuantitiesUnit().size
        myFoodRepository.addQuantityUnit("testQuantity")
        val quantitiesUnit = myFoodRepository.getQuantitiesUnit()
        val newSize = quantitiesUnit.size
        Assert.assertEquals(initSize + 1, newSize)
        Assert.assertEquals(quantitiesUnit[newSize - 1].quantityUnit, "testQuantity")
        myFoodRepository.updateQuantityUnit(
            "modifiedQuantity",
            quantitiesUnit[newSize - 1].idQuantityUnit.toString()
        )
        val modifiedQuantities = myFoodRepository.getQuantitiesUnit()
        Assert.assertEquals(modifiedQuantities[newSize - 1].quantityUnit, "modifiedQuantity")
        myFoodRepository.deleteQuantityUnit(modifiedQuantities[newSize - 1].idQuantityUnit.toString())
        val finalSize = myFoodRepository.getQuantitiesUnit().size
        Assert.assertEquals(initSize, finalSize)
    }

    @Test
    fun getUserId__updateUserId() {
        val initUser = myFoodRepository.getUserId()
        myFoodRepository.updateUserId("999")
        val userModified = myFoodRepository.getUserId()
        Assert.assertEquals(userModified, "999")
        myFoodRepository.updateUserId(initUser)
        val userModified2 = myFoodRepository.getUserId()
        Assert.assertEquals(userModified2, initUser)
    }

    @Test
    fun getLanguages() {
        val languages = myFoodRepository.getLanguages()
        Assert.assertEquals(languages[0], "Español")
        Assert.assertEquals(languages[1], "English")
        Assert.assertEquals(languages[2], "Français")
        Assert.assertEquals(languages[3], "Deutsch")
        Assert.assertEquals(languages[4], "Català")
    }

    @Test
    fun getCurrencies() {
        val currencySpanish = myFoodRepository.getCurrencies(1)
        Assert.assertEquals(currencySpanish[0], "€")
        val currencyEnglish = myFoodRepository.getCurrencies(2)
        Assert.assertEquals(currencyEnglish[0], "£")
        Assert.assertEquals(currencyEnglish[1], "$")
        val currencyFrench = myFoodRepository.getCurrencies(3)
        Assert.assertEquals(currencyFrench[0], "€")
        val currencyGerman = myFoodRepository.getCurrencies(4)
        Assert.assertEquals(currencyGerman[0], "€")
        val currencyCatalan = myFoodRepository.getCurrencies(5)
        Assert.assertEquals(currencyCatalan[0], "€")
    }

    @Test
    fun getCurrentCurrency__updateCurrentCurrency() {
        val initValue = myFoodRepository.getCurrentCurrency()
        myFoodRepository.updateCurrentCurrency("$")
        val modifiedValue = myFoodRepository.getCurrentCurrency()
        Assert.assertEquals(modifiedValue, "$")
        myFoodRepository.updateCurrentCurrency(initValue)
        val modifiedValue2 = myFoodRepository.getCurrentCurrency()
        Assert.assertEquals(modifiedValue2, initValue)
    }

    @Test
    fun getStorePlaces__updateStorePlace__addStorePlace__deleteStorePlace() {
        val initSize = myFoodRepository.getStorePlaces().size
        myFoodRepository.addStorePlace("testStorePlace")
        val storePlaces = myFoodRepository.getStorePlaces()
        val newSize = storePlaces.size
        Assert.assertEquals(initSize + 1, newSize)
        Assert.assertEquals(storePlaces[newSize - 1].storePlace, "testStorePlace")
        myFoodRepository.updateStorePlace(
            "modifiedStorePlace",
            storePlaces[newSize - 1].idStorePlace.toString()
        )
        val modifiedStorePlaces = myFoodRepository.getStorePlaces()
        Assert.assertEquals(modifiedStorePlaces[newSize - 1].storePlace, "modifiedStorePlace")
        myFoodRepository.deleteStorePlace(modifiedStorePlaces[newSize - 1].idStorePlace.toString())
        val finalSize = myFoodRepository.getStorePlaces().size
        Assert.assertEquals(initSize, finalSize)
    }

    @Test
    fun insertPantry__getPantryProduct__updatePantry__deletePantry() {
        val responseInsert = myFoodRepository.insertPantry(
            "8423483234004",
            "Leche", "33.30", "g", "Armario", "100.40", "43.30",
            "1/12/2022", "9/12/2022", "QIJÑMK.#2Afksf32...",
            "Hacendado", myFoodRepository.getUserId()
        ).getOrAwaitValueTest()
        val idInserted = responseInsert.value
        val responseProduct = myFoodRepository.getPantryProduct(idInserted).getOrAwaitValueTest()
        Assert.assertEquals(responseProduct.barcode, "8423483234004")
        Assert.assertEquals(responseProduct.name, "Leche")
        Assert.assertEquals(responseProduct.quantity, "33.30")
        Assert.assertEquals(responseProduct.quantityUnit, "g")
        Assert.assertEquals(responseProduct.storePlace, "Armario")
        Assert.assertEquals(responseProduct.weight, "100.40")
        Assert.assertEquals(responseProduct.price, "43.30")
        Assert.assertEquals(responseProduct.expiredDate, "1/12/2022")
        Assert.assertEquals(responseProduct.preferenceDate, "9/12/2022")
        Assert.assertEquals(responseProduct.image, "QIJÑMK.#2Afksf32...")
        Assert.assertEquals(responseProduct.brand, "Hacendado")


        myFoodRepository.updatePantry(
            "8423483234005",
            "Leche desnatada", "33.35", "Kg", "Nevera", "100.45", "43.35",
            "1/12/2025", "9/12/2025", "QIJÑMK.#2Afksf32555...",
            "Dani", idInserted
        ).getOrAwaitValueTest()

        val responseProduct2 = myFoodRepository.getPantryProduct(idInserted).getOrAwaitValueTest()
        Assert.assertEquals(responseProduct2.barcode, "8423483234005")
        Assert.assertEquals(responseProduct2.name, "Leche desnatada")
        Assert.assertEquals(responseProduct2.quantity, "33.35")
        Assert.assertEquals(responseProduct2.quantityUnit, "Kg")
        Assert.assertEquals(responseProduct2.storePlace, "Nevera")
        Assert.assertEquals(responseProduct2.weight, "100.45")
        Assert.assertEquals(responseProduct2.price, "43.35")
        Assert.assertEquals(responseProduct2.expiredDate, "1/12/2025")
        Assert.assertEquals(responseProduct2.preferenceDate, "9/12/2025")
        Assert.assertEquals(responseProduct2.image, "QIJÑMK.#2Afksf32555...")
        Assert.assertEquals(responseProduct2.brand, "Dani")

        myFoodRepository.deletePantry(idInserted).getOrAwaitValueTest()

        val responseProduct3 = myFoodRepository.getPantryProduct(idInserted).getOrAwaitValueTest()
        Assert.assertEquals(responseProduct3.status, "KO")
    }

    @Test
    fun getOpenFoodProduct() {
        val urlOpenFood = "https://es.openfoodfacts.org/api/v0/product/08480000108487.json"
        val response = myFoodRepository.getOpenFoodProduct(urlOpenFood).getOrAwaitValueTest()
        Assert.assertEquals(response.product.barcode, "8480000108487")
        Assert.assertEquals(response.product.brands, "Hacendado")
        Assert.assertEquals(response.product.productName, "Leche desnatada")
        Assert.assertEquals(response.product.genericName, "Leche desnatada de vaca")
        Assert.assertEquals(
            response.product.image,
            "https://images.openfoodfacts.org/images/products/848/000/010/8487/front_es.49.200.jpg"
        )
    }

    @Test
    fun insertShop__getShopProduct__updateShop__deleteShop() {
        val responseInsert = myFoodRepository.insertShop(
            "testShop",
            "20.00", "g", myFoodRepository.getUserId()
        ).getOrAwaitValueTest()
        val idInsert = responseInsert.value
        val responseProduct = myFoodRepository.getShopProduct(idInsert).getOrAwaitValueTest()
        Assert.assertEquals(responseProduct.name, "testShop")
        Assert.assertEquals(responseProduct.quantity, "20.00")
        Assert.assertEquals(responseProduct.quantityUnit, "g")

        myFoodRepository.updateShop("testShop2", "30.00", "Bolsa", idInsert).getOrAwaitValueTest()
        val responseProduct2 = myFoodRepository.getShopProduct(idInsert).getOrAwaitValueTest()
        Assert.assertEquals(responseProduct2.name, "testShop2")
        Assert.assertEquals(responseProduct2.quantity, "30.00")
        Assert.assertEquals(responseProduct2.quantityUnit, "Bolsa")

        myFoodRepository.deleteShop(idInsert).getOrAwaitValueTest()
        val responseProduct3 = myFoodRepository.getShopProduct(idInsert).getOrAwaitValueTest()
        Assert.assertEquals(responseProduct3.status, "KO")
    }

    @Test
    fun changeEmail__getEmail() {
        val insertUser = myFoodRepository.insertUser(
            "test", "test",
            "a@test.com", "22222"
        ).getOrAwaitValueTest()
        val user = insertUser.value
        val initValue = myFoodRepository.getEmail(user).getOrAwaitValueTest()
        myFoodRepository.changeEmail("test@test.com", user).getOrAwaitValueTest()
        val modifiedValue = myFoodRepository.getEmail(user).getOrAwaitValueTest()
        Assert.assertEquals(modifiedValue.value, "test@test.com")
        myFoodRepository.changeEmail(initValue.value, user).getOrAwaitValueTest()
        val modifiedValue2 = myFoodRepository.getEmail(user).getOrAwaitValueTest()
        Assert.assertEquals(modifiedValue2.value, initValue.value)
        myFoodRepository.deleteUser(user).getOrAwaitValueTest()
        val response = myFoodRepository.getEmail(user)
        Assert.assertEquals(response.value, null)

    }

    @Test
    fun changePassword__getPassword() {
        val insertUser = myFoodRepository.insertUser(
            "test", "test",
            "test@test.com", "22222"
        ).getOrAwaitValueTest()
        val user = insertUser.value
        val initValue = myFoodRepository.getPassword(user).getOrAwaitValueTest()
        myFoodRepository.changePassword("1234", user).getOrAwaitValueTest()
        val modifiedValue = myFoodRepository.getPassword(user).getOrAwaitValueTest()
        Assert.assertEquals(modifiedValue.value, "1234")
        myFoodRepository.changePassword(initValue.value, user).getOrAwaitValueTest()
        val modifiedValue2 = myFoodRepository.getPassword(user).getOrAwaitValueTest()
        Assert.assertEquals(modifiedValue2.value, initValue.value)
        myFoodRepository.deleteUser(user).getOrAwaitValueTest()
        val response = myFoodRepository.getEmail(user)
        Assert.assertEquals(response.value, null)
    }

    @Test
    fun getExpirationList() {
        val insertUser = myFoodRepository.insertUser(
            "test", "test",
            "test@test.com", "22222"
        ).getOrAwaitValueTest()
        val user = insertUser.value
        val currentDate = LocalDateTime.now()
        val almostExpiredDate = currentDate.plusDays(5)
        var day = almostExpiredDate.dayOfMonth
        var month = almostExpiredDate.monthValue
        var year = almostExpiredDate.year
        val almostExpiredDateString = "$day/$month/$year"

        val expiredDate = currentDate.plusDays(-10)
        day = expiredDate.dayOfMonth
        month = expiredDate.monthValue
        year = expiredDate.year
        val expiredDateString = "$day/$month/$year"

        val okDate = currentDate.plusDays(20)
        day = okDate.dayOfMonth
        month = okDate.monthValue
        year = okDate.year
        val okDateString = "$day/$month/$year"

        val insertPantry1 = myFoodRepository.insertPantry(
            "", "expired", "233",
            "g", "Armario", "32.00", "32.00", expiredDateString,
            "", "ñaksñlkajf", "", user
        ).getOrAwaitValueTest()

        val insertPantry2 = myFoodRepository.insertPantry(
            "", "almostExpired", "233",
            "g", "Armario", "32.00", "32.00", almostExpiredDateString,
            "", "ñaksñlkajf", "", user
        ).getOrAwaitValueTest()

        val insertPantry3 = myFoodRepository.insertPantry(
            "asdfñ", "OK", "233",
            "g", "Armario", "32.00", "32.00", okDateString,
            "", "ñaksñlkajf", "", user
        ).getOrAwaitValueTest()

        var all = myFoodRepository.getExpirationList("All", user).getOrAwaitValueTest()
        Assert.assertEquals(all.products.size, 3)
        val expired = myFoodRepository.getExpirationList("expired", user).getOrAwaitValueTest()
        Assert.assertEquals(expired.products.size, 1)
        val almostExpired =
            myFoodRepository.getExpirationList("0to10days", user).getOrAwaitValueTest()
        Assert.assertEquals(almostExpired.products.size, 1)
        val ok = myFoodRepository.getExpirationList("more10days", user).getOrAwaitValueTest()
        Assert.assertEquals(ok.products.size, 1)

        myFoodRepository.deletePantry(insertPantry1.value).getOrAwaitValueTest()
        myFoodRepository.deletePantry(insertPantry2.value).getOrAwaitValueTest()
        myFoodRepository.deletePantry(insertPantry3.value).getOrAwaitValueTest()

        all = myFoodRepository.getExpirationList("All", user).getOrAwaitValueTest()
        Assert.assertEquals(all.products.size, 0)

        myFoodRepository.deleteUser(user).getOrAwaitValueTest()
        val response = myFoodRepository.getEmail(user)
        Assert.assertEquals(response.value, null)
    }

    @Test
    fun removeExpired() {
        val insertUser = myFoodRepository.insertUser(
            "test", "test",
            "test@test.com", "22222"
        ).getOrAwaitValueTest()
        val user = insertUser.value
        myFoodRepository.insertPantry(
            "asdfñ", "ñjkajf", "233",
            "g", "Armario", "32.00", "32.00", "1/1/2007",
            "", "ñaksñlkajf", "", user
        ).getOrAwaitValueTest()
        val expired = myFoodRepository.getExpirationList("expired", user).getOrAwaitValueTest()
        Assert.assertEquals(expired.products.size, 1)
        myFoodRepository.removeExpired(user).getOrAwaitValueTest()
        val expired2 = myFoodRepository.getExpirationList("expired", user).getOrAwaitValueTest()
        Assert.assertEquals(expired2.products.size, 0)
        myFoodRepository.deleteUser(user).getOrAwaitValueTest()
        val response = myFoodRepository.getEmail(user)
        Assert.assertEquals(response.value, null)
    }

    @Test
    fun login() {
        val insertUser = myFoodRepository.insertUser(
            "test", "test",
            "test@test.com", "22222"
        ).getOrAwaitValueTest()
        val user = insertUser.value
        val loginRes = myFoodRepository.login("test", "xxxx").getOrAwaitValueTest()
        Assert.assertEquals(loginRes.status, "KO")
        val loginRes2 = myFoodRepository.login("test", "22222").getOrAwaitValueTest()
        Assert.assertEquals(loginRes2.status, "OK")
        myFoodRepository.deleteUser(user).getOrAwaitValueTest()
        val response = myFoodRepository.getEmail(user)
        Assert.assertEquals(response.value, null)
    }

    @Test
    fun getPantryList() {
        val insertUser = myFoodRepository.insertUser(
            "test", "test",
            "test@test.com", "22222"
        ).getOrAwaitValueTest()
        val user = insertUser.value
        val list = myFoodRepository.getPantryList(user).getOrAwaitValueTest()
        Assert.assertEquals(list.products.size, 0)
        val insertPantry1 = myFoodRepository.insertPantry(
            "asdfñ", "pantry1", "233",
            "g", "Armario", "32.00", "32.00", "1/1/2007",
            "", "ñaksñlkajf", "", user
        ).getOrAwaitValueTest()
        val insertPantry2 = myFoodRepository.insertPantry(
            "asdfñ", "pantry2", "233",
            "g", "Armario", "32.00", "32.00", "1/1/2007",
            "", "ñaksñlkajf", "", user
        ).getOrAwaitValueTest()
        val insertPantry3 = myFoodRepository.insertPantry(
            "asdfñ", "pantry3", "233",
            "g", "Armario", "32.00", "32.00", "1/1/2007",
            "", "ñaksñlkajf", "", user
        ).getOrAwaitValueTest()
        val list2 = myFoodRepository.getPantryList(user).getOrAwaitValueTest()
        Assert.assertEquals(list2.products.size, 3)
        Assert.assertEquals(list2.products[0].name, "pantry1")
        Assert.assertEquals(list2.products[1].name, "pantry2")
        Assert.assertEquals(list2.products[2].name, "pantry3")
        myFoodRepository.deletePantry(insertPantry1.value).getOrAwaitValueTest()
        myFoodRepository.deletePantry(insertPantry2.value).getOrAwaitValueTest()
        myFoodRepository.deletePantry(insertPantry3.value).getOrAwaitValueTest()

        val list3 = myFoodRepository.getPantryList(user).getOrAwaitValueTest()
        Assert.assertEquals(list3.products.size, 0)

        myFoodRepository.deleteUser(user).getOrAwaitValueTest()
        val response = myFoodRepository.getEmail(user)
        Assert.assertEquals(response.value, null)
    }

    @Test
    fun getRecipe() {
        val response = myFoodRepository.getRecipe("1", "5").getOrAwaitValueTest()
        Assert.assertEquals(response.title, "Mandonguilles de carn")
        Assert.assertEquals(response.portions, "4")
        Assert.assertEquals(
            response.ingredients,
            "11 ingredients: 250 g Carn d'Anyojo - 250 g Carn de Boví - 1/2 pa d'hamburguesa - 3 cullerades llet - 1 ceba tendra - 1/2 Pebrot verd - sal - Pebre - 1/2 l Brou de carn - Oli de Girasol "
        )
        Assert.assertEquals(
            response.directions,
            "Instruccions: 1) Aboquem en un bol juntament amb la carn de vedella picada. 2) Salpebrem al nostre gust. 3) En un altre bol triturem el pa de mitjana Burger i barregem amb les tres cullerades de llet. Barregem el pa amb la carn. 4) Formem una bola i després comencem a preparar les mandonguilles de carn. 5) Un cop tinguem totes posem oli vegetal a escalfar en una paella. 6) Passem les mandonguilles de carn per farina i daurem a la paella. 7) En una cassola escalfem la ceba tendra i el pebrot verd que haurem picat finament prèviament. 8) Quan estigui fet al nostre gust aboquem el brou incorporem les mandonguilles i deixem bullir a foc baix-mitjà durant 20-25 minuts amb la cassola tapada. 9) Rectifiquem el punt de sal si calgués. 10) En el que es fan les mandonguilles al foc col·loquem les patates en una safata escampem una mica de farigola o romaní fresc per sobre i escalfem al forn durant 10 minuts. 11) Servim les mandonguilles de carn acompanyades per les patates. . "
        )
    }

    @Test
    fun getRecipeList() {
        val response = myFoodRepository.getRecipeList("5").getOrAwaitValueTest()
        Assert.assertEquals(response.recipes.size, 704)
        Assert.assertEquals(response.recipes[0].id, "1")
        Assert.assertEquals(response.recipes[0].title, "Mandonguilles de carn")
    }

    @Test
    fun getRecipesSuggested() {
        val insertUser = myFoodRepository.insertUser(
            "test", "test",
            "test@test.com", "22222"
        ).getOrAwaitValueTest()
        val user = insertUser.value

        val insertPantry1 = myFoodRepository.insertPantry(
            "asdfñ", "Aceite de oliva virgen extra", "233",
            "g", "Armario", "32.00", "32.00", "1/1/2007",
            "", "ñaksñlkajf", "", user
        ).getOrAwaitValueTest()

        val list1 = myFoodRepository.getPantryList(user).getOrAwaitValueTest()
        Assert.assertEquals(list1.products.size, 1)

        val recipes = myFoodRepository.getRecipesSuggested("5", user).getOrAwaitValueTest()
        Assert.assertEquals(recipes.recipes[0].id, "4")
        Assert.assertEquals(recipes.recipes[0].title, "Pa de pessic d'albercocs")

        myFoodRepository.deletePantry(insertPantry1.value).getOrAwaitValueTest()
        val list2 = myFoodRepository.getPantryList(user).getOrAwaitValueTest()
        Assert.assertEquals(list2.products.size, 0)

        myFoodRepository.deleteUser(user).getOrAwaitValueTest()
        val response = myFoodRepository.getEmail(user)
        Assert.assertEquals(response.value, null)
    }

    @Test
    fun getShopList() {
        val insertUser = myFoodRepository.insertUser(
            "test", "test",
            "test@test.com", "22222"
        ).getOrAwaitValueTest()
        val user = insertUser.value
        val responseInsert = myFoodRepository.insertShop(
            "testShop",
            "20.00", "g", user
        ).getOrAwaitValueTest()
        val idInsert1 = responseInsert.value
        val get1 = myFoodRepository.getShopList(user).getOrAwaitValueTest()
        val size1 = get1.products.size
        val responseInsert2 = myFoodRepository.insertShop(
            "testShop",
            "20.00", "g", user
        ).getOrAwaitValueTest()
        val idInsert2 = responseInsert2.value
        val get2 = myFoodRepository.getShopList(user).getOrAwaitValueTest()
        Assert.assertEquals(get2.products.size, size1 + 1)
        myFoodRepository.deleteShop(idInsert1).getOrAwaitValueTest()
        myFoodRepository.deleteShop(idInsert2).getOrAwaitValueTest()
        val get3 = myFoodRepository.getShopList(user).getOrAwaitValueTest()
        Assert.assertEquals(get3.products.size, 0)
        myFoodRepository.deleteUser(user).getOrAwaitValueTest()
        val response = myFoodRepository.getEmail(user)
        Assert.assertEquals(response.value, null)
    }

    @Test
    fun insertUser() {
        val insertUser = myFoodRepository.insertUser(
            "test", "test",
            "test@test.com", "22222"
        ).getOrAwaitValueTest()
        val user = insertUser.value
        val loginRes = myFoodRepository.login("test", "22222").getOrAwaitValueTest()
        Assert.assertEquals(loginRes.status, "OK")
        myFoodRepository.deleteUser(user).getOrAwaitValueTest()
        val response = myFoodRepository.getEmail(user)
        Assert.assertEquals(response.value, null)
    }

    @Test
    fun getNutrients() {
        val response = myFoodRepository.getNutrients("5").getOrAwaitValueTest()
        Assert.assertEquals(response.nutrients[0], "General")
        Assert.assertEquals(response.nutrients[1], "Vitamines")
        Assert.assertEquals(response.nutrients[2], "Minerals")
        Assert.assertEquals(response.nutrients[3], "Àcids grassos")
    }

    @Test
    fun getNutrientsByType() {
        //We select group 2: Vitamins
        val response = myFoodRepository.getNutrientsByType(
            "2",
            "2004",
            "5"
        ).getOrAwaitValueTest()
        Assert.assertEquals(response.foodNutrients[0].column, "Calcio(mg)")
        Assert.assertEquals(response.foodNutrients[0].value, "110.00")
        Assert.assertEquals(response.foodNutrients[1].column, "Hierro(mg)")
        Assert.assertEquals(response.foodNutrients[1].value, "0.09")
        Assert.assertEquals(response.foodNutrients[2].column, "Yodo(µg)")
        Assert.assertEquals(response.foodNutrients[2].value, "0.00")
        Assert.assertEquals(response.foodNutrients[3].column, "Magnesio(mg)")
        Assert.assertEquals(response.foodNutrients[3].value, "11.40")
        Assert.assertEquals(response.foodNutrients[4].column, "Cinc(mg)")
        Assert.assertEquals(response.foodNutrients[4].value, "0.00")
        Assert.assertEquals(response.foodNutrients[5].column, "Sodio(mg)")
        Assert.assertEquals(response.foodNutrients[5].value, "95.00")
        Assert.assertEquals(response.foodNutrients[6].column, "Potasio(mg)")
        Assert.assertEquals(response.foodNutrients[6].value, "183.00")
        Assert.assertEquals(response.foodNutrients[7].column, "Fósforo(mg)")
        Assert.assertEquals(response.foodNutrients[7].value, "90.00")
        Assert.assertEquals(response.foodNutrients[8].column, "Selenio(µg)")
        Assert.assertEquals(response.foodNutrients[8].value, "2.30")
    }
}