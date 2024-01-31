package com.example.ngebacot.core.data.local.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.ngebacot.core.domain.model.UserModel

object AuthLocalDatastore {
    /*
    *   Untuk Menyimpan Authentication Token
    */
    private const val PREF_NAME = "MyAppPrefrences"
    private const val KEY_JWT_TOKEN = "jwtToken"
    private const val KEY_USER_ID = "userId"
    private const val KEY_USERNAME = "username"
    private const val KEY_EMAIL = "email"
    private const val KEY_NAME = "name"
    private const val KEY_COVERPIC = "coverpic"
    private const val KEY_PROFILEPIC = "profilepic"
    private const val KEY_CITY = "city"
    private const val KEY_WEBSITE = "website"
    private const val KEY_CREATED_AT = "created_at"

    private fun getEncryptedSharedPreferences(context: Context)
            : SharedPreferences{
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            PREF_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    /*
    * Save Token
    */
    fun saveToken(context: Context, token: String){
        val sharedPreferences = getEncryptedSharedPreferences(context)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_JWT_TOKEN, token)
        editor.apply()
    }
    /*
    * Get Token
    */
    fun getToken(context: Context): String?{
        val sharedPreferences = getEncryptedSharedPreferences(context)
        return sharedPreferences.getString(KEY_JWT_TOKEN, null)
    }

    /*
    * Save Token
    */
    fun clearToken(context: Context){
        val sharedPreferences = getEncryptedSharedPreferences(context)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(KEY_JWT_TOKEN)
        editor.apply()
    }

    /*
    * Save data user in local storage
    */

    fun saveUser(context: Context, user: UserModel?) {
        if (user != null) {
            val sharedPreferences = getEncryptedSharedPreferences(context)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt(KEY_USER_ID, user.id)
            editor.putString(KEY_USERNAME, user.username ?: "")
            editor.putString(KEY_EMAIL, user.email ?: "")
            editor.putString(KEY_NAME, user.name ?: "")
            editor.putString(KEY_COVERPIC, user.coverpic ?: "")
            editor.putString(KEY_PROFILEPIC, user.profilepic ?: "")
            editor.putString(KEY_CITY, user.city ?: "")
            editor.putString(KEY_WEBSITE, user.website ?: "")
            editor.putString(KEY_CREATED_AT, user.created_at ?: "")
            editor.apply()
        }
    }


    fun getUser(context: Context): UserModel? {
        val sharedPreferences = getEncryptedSharedPreferences(context)
        val userId = sharedPreferences.getInt(KEY_USER_ID, -1)
        if (userId != -1) {
            val username = sharedPreferences.getString(KEY_USERNAME, "")
            val email = sharedPreferences.getString(KEY_EMAIL, "")
            val name = sharedPreferences.getString(KEY_NAME, "")
            val coverpic = sharedPreferences.getString(KEY_COVERPIC, "")
            val profilepic = sharedPreferences.getString(KEY_PROFILEPIC, "")
            val city = sharedPreferences.getString(KEY_CITY, "")
            val website = sharedPreferences.getString(KEY_WEBSITE, "")
            val created_at = sharedPreferences.getString(KEY_CREATED_AT, "")
            return UserModel(userId, username ?: "", email ?: "", name, coverpic, profilepic, city, website, created_at ?: "")
        }
        return null
    }


    fun clearUserData(context: Context) {
        val sharedPreferences = getEncryptedSharedPreferences(context)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(KEY_USER_ID)
        editor.remove(KEY_USERNAME)
        editor.remove(KEY_EMAIL)
        editor.remove(KEY_NAME)
        editor.remove(KEY_COVERPIC)
        editor.remove(KEY_PROFILEPIC)
        editor.remove(KEY_CITY)
        editor.remove(KEY_WEBSITE)
        editor.remove(KEY_CREATED_AT)
        editor.apply()
    }

}