package com.myfood.constants

class Constant {
    companion object {

        //URL Openfood
        const val OPEN_FOOD_URL = "https://es.openfoodfacts.org/api/v0/product/"

        //General
        const val KO = "KO"
        const val OK_INT = 1
        const val OK = "OK"
        const val MODE_SCAN = 2
        const val MODE_UPDATE = 1
        const val MODE_ADD = 0
        const val CONST_DPICKER = "DataPicker"
        const val CONST_SCAN = "SCAN"
        const val GENERAL_NUTRIENT = "1"

        //Fields
        const val FIELD_NAME = "name"
        const val FIELD_PASSWORD = "password"
        const val FIELD_SIGN_UP = "signUp"
        const val FIELD_FORGOTTEN_PASSWORD = "forgotPass"
        const val FIELD_EMAIL = "Email"
        const val FIELD_SURNAMES = "surnames"
        const val FIELD_CONFIRM_PASSWORD = "confirmPass"
        const val FIELD_SEARCH = "search"
        const val FIELD_BARCODE = "barcode"
        const val FIELD_BRAND = "brand"
        const val FIELD_PLACE = "place"
        const val FIELD_PRICE = "price"
        const val FIELD_QUANTITY = "quantity"
        const val FIELD_QUANTITY_UNIT = "quantityUnit"
        const val FIELD_WEIGHT = "weight"
        const val FIELD_EXPIRATION_DATE = "expirationDate"
        const val FIELD_PREFERENCE_DATE = "preferenceDate"

        //Buttons
        const val BTN_LOGIN = "login"
        const val BTN_SEND_LINK_FORGOTTEN_PASSWORD = "sendLinkBtn"
        const val BTN_CHANGE_IMAGE = "changeImage"
        const val BTN_SCAN = "scan"
        const val BTN_ADD_PANTRY = "addBtnPantry"
        const val BTN_UPDATE_PANTRY = "updateBtnPantry"
        const val BTN_ALL = "all"
        const val BTN_EXPIRED = "expirated"
        const val BTN_0_TO_10_DAYS = "zeroToTenDays"
        const val BTN_MORE_10_DAYS = "moreThanTenDays"
        const val BTN_REMOVE_ALL_EXPIRED = "removeAllExpired"
        const val BTN_SUGGESTIONS = "suggestions"
        const val BTN_LANG_AND_CURR = "changeLangAndCurrency"
        const val BTN_CHANGE_EMAIL = "changeEmail"
        const val BTN_CHANGE_PASSWORD = "changePassword"
        const val BTN_STORE_PLACES = "places"
        const val BTN_QUANTITY_UNIT = "quantitUnitsConfig"
        const val BTN_ADD_STORE_PLACE = "addPlaceBtn"
        const val BTN_ADD_QUANTITY_UNIT = "addQuantityBtn"
        const val BTN_UPDATE_STORE_PLACE = "updateStorePlaceBtn"
        const val BTN_UPDATE_QUANTITY_UNIT = "updateQuantityBtn"
        const val BTN_YES = "yes"
        const val BTN_NO = "no"
        const val BTN_ADD_SHOPPING = "addShopListBtn"
        const val BTN_UPDATE_SHOPPING = "updateShopListBtn"
        const val BTN_NUTRIENTS = "nutrients"
        const val BTN_CHARACTERISTICS = "characteristics"

        //Messages
        const val MSG_INCORRECT_LOGIN = "loginIncorrect"
        const val MSG_NAME_IS_EMPTY = "emptyNameField"
        const val MSG_SURNAMES_IS_EMPTY = "emptySurnameField"
        const val MSG_EMAIL_IS_EMPTY = "emptyEmailField"
        const val MSG_EMAIL_FORMAT_INCORRECT = "formatEmailIncorr"
        const val MSG_PASSWORD_IS_EMPTY = "emptyPasswordField"
        const val MSG_CONFIRM_PASSWORD_IS_EMPTY = "emptyConfirmPasswordField"
        const val MSG_NOT_MATCH_PASSWORDS = "noSamePasswords"
        const val MSG_USER_INSERTED = "InsertUserSuccess"
        const val MSG_FORGOTTEN_PASSWORD_TEXT = "forgotPassText"
        const val MSG_FORGOTTEN_PASSWORD_TEXT_OK = "forgotPasswordSended"
        const val MSG_FORGOTTEN_PASSWORD_TEXT_KO = "forgotPassTextKO"
        const val MSG_PRODUCT_NOT_FOUND = "productNotFound"
        const val MSG_NOT_EMPTY_NAME = "requiredName"
        const val MSG_NOT_EMPTY_EXPIRED_DATE = "emptyExpiredDate"
        const val MSG_NAME_REQUIRED = "requiredName"
        const val MSG_EMAIL_REQUIRED_CONF = "emptyEmail"
        const val MSG_EMAIL_FORMAT_INCORRECT_CONF = "emptyEmail"
        const val MSG_LANG_AND_CURR_UPDATED = "langAndCurrUpdated"
        const val MSG_EMAIL_UPDATED = "emailUpdated"
        const val MSG_EMAIL_NOT_UPDATED = "emailNotUpdated"
        const val MSG_STORE_PLACE_REQUIRED = "requiredStorePlace"
        const val MSG_QUANTIY_UNIT_REQUIRED = "requiredQuantityUnit"
        const val MSG_PASSWORD_UPDATED = "passwordUpdated"
        const val MSG_PASSWORD_NOT_UPDATED = "passwordNotUpdated"
        const val MSG_REMOVE_ALL_EXPIRED_QUESTION = "removeAllExpiredQuestion"
        const val MSG_DELETE_PANTRY_QUESTION = "deletePantryQuestion"

        //Titles
        const val TITLE_FORGOTTEN_PASSWORD = "forgotPass"
        const val TITLE_PANTRY_LIST = "purchaseList"
        const val TITLE_ADD_PANTRY = "addPantryTitle"
        const val TITLE_UPDATE_PANTRY = "updatePantryTitle"
        const val TITLE_SHOPPING_LIST = "shoppingList"
        const val TITLE_EXPIRATION_LIST = "expirationTitle"
        const val TITLE_RECIPE_LIST = "recipesTitle"
        const val TITLE_RECIPE = "recipeTitle"
        const val TITLE_CONFIG = "configTitle"
        const val TITLE_QUANTITY_UNIT_LIST = "quantitUnitsConfig"
        const val TITLE_STORE_PLACES_LIST = "places"
        const val TITLE_ADD_STORE_PLACE = "addPlaceTitle"
        const val TITLE_ADD_QUANTITY_UNIT = "addQuantityTitle"
        const val TITLE_UPDATE_STORE_PLACE = "updateStorePlaceTitle"
        const val TITLE_UPDATE_QUANTITY_UNIT = "updateQuantityTitle"
        const val TITLE_ADD_SHOPPING = "addShopListTitle"
        const val TITLE_UPDATE_SHOPPING = "updateShopListTitle"


        //Labels
        const val LABEL_TOTAL = "total"
        const val LABEL_NAME = "name"
        const val LABEL_REMAIN = "remain"
        const val LABEL_PRICE = "price"
        const val LABEL_LANGUAGE = "language"
        const val LABEL_CURRENCY = "currency"
        const val LABEL_STORE_PLACE = "place"
        const val LABEL_QUANTITY = "quantity"
        const val LABEL_QUANTITY_UNIT = "quantityUnit"
        const val LABEL_PORTIONS = "portions"
        const val LABEL_INGREDIENTS = "ingredients"
        const val LABEL_DIRECTIONS = "directions"
        const val LABEL_BARCODE = "barcode"
        const val LABEL_BRAND = "brand"
        const val LABEL_PLACE = "place"
        const val LABEL_WEIGHT = "weight"
        const val LABEL_EXPIRATION_DATE = "expirationDate"
        const val LABEL_PREFERENCE_DATE = "preferenceDate"

        //Menu
        const val MENU_PANTRY = "menuPurchase"
        const val MENU_SHOPPING = "menuShopping"
        const val MENU_EXPIRATION = "menuExpiration"
        const val MENU_RECIPE = "menuRecipe"
        const val MENU_CONFIG = "menuConfig"

        //Color
        const val COLOR_TURQUOISE = "#009691"
    }
}