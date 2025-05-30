package com.example.trabajopractico_jonatanpalavecino.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object UserStorage {

    private const val PREFS_NAME = "user_prefs"
    private const val  USERS_KEY = "users_list"
    private const val IS_LOGGED_IN = "logged_in"

    private val gson = Gson()

    //funcion que recibe email y pass, verifica con la lista traida de la sharedPreferences y retorna true o false dependiendo si encuentra al usuario
    fun loginUser(context: Context, email: String, password: String): Boolean {

        val usersList = getUsers(context)
        val userFound = usersList.find { it.email == email && it.password == password}

        return if (userFound != null) {
            true
        } else {
            false
        }
    }

    //funcion que registra al usuario y guarda el usuario junto a los anteriormente cargados
    fun registerUser(context: Context, newUser: Users) {

        //toma el sharedPreferences del contexto en el que la función es llamada
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        //agregamos el usuario a la lista
        val usersList = getUsers(context).toMutableList()
        usersList.add(newUser)
        val json = gson.toJson(usersList)
        editor.putString(USERS_KEY, json)
        editor.apply()
    }

    //funcion que te devuelve una lista de usuarios
    fun getUsers(context: Context): List<Users> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(USERS_KEY, null) ?: return emptyList()

        val type = object : TypeToken<List<Users>>() {}.type
        return gson.fromJson(json, type)
    }

    //funcion que setea la eleccion del usuario de mantener la sesion activa o no en funcion de su eleccion
    fun toggleSession(context: Context, isLoggedIn: Boolean) {

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    //funcion que verifica la eleccion del usuario de si eligio mantener la sesion o no
    fun isLoggedIn(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(IS_LOGGED_IN, false)
    }
}