package com.example.ngebacot.core.data.local.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


object AuthLocalDatastore {
    /*
    *   Untuk Menyimpan Authentication Token
    */
    private const val PREF_NAME = "MyAppPrefrences"
    private const val KEY_JWT_TOKEN = "jwtToken"

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
}