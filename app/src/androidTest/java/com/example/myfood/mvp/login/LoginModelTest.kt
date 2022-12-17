package com.example.myfood.mvp.login

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myfood.constants.Constant
import com.example.myfood.getOrAwaitValueTest
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginModelTest : TestCase() {
    private lateinit var loginModel: LoginModel
    private lateinit var context: Context


    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        loginModel = LoginModel()
        loginModel.getInstance(context)
    }

    @Test
    fun getTranslations() {
        val response = loginModel.getLanguages()
    }

    @Test
    fun getLanguages() = runBlocking {
        val response = loginModel.getLanguages()
        assertEquals(response[0], "Español")
        assertEquals(response[1], "English")
        assertEquals(response[2], "Français")
        assertEquals(response[3], "Deutsch")
        assertEquals(response[4], "Català")
    }

    @Test
    fun getCurrentLanguage() {
        val currentLanguage = loginModel.getCurrentLanguage()

        loginModel.updateCurrentLanguage("1")

        val newCurrentLanguage = loginModel.getCurrentLanguage()
        assertEquals(newCurrentLanguage, "1")

        loginModel.updateCurrentLanguage(currentLanguage)
        val currentLanguage2 = loginModel.getCurrentLanguage()
        assertEquals(currentLanguage2, currentLanguage)
    }

    @Test
    fun updateCurrentLanguage() {
    }

    @Test
    fun updateUserId() {

    }

    @Test
    fun login_CORRECT() = runBlocking {
        val result = loginModel.login("vic", "new").getOrAwaitValueTest()
        assertEquals(result.status, Constant.OK)
    }

    @Test
    fun login_INCORRECT() = runBlocking {
        val result = loginModel.login("vic", "ne").getOrAwaitValueTest()
        assertEquals(result.status, Constant.KO)
    }
}