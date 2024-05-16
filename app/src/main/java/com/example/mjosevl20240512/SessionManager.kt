import android.content.Context
import android.content.SharedPreferences

import com.example.mjosevl20240512.data.Usuario
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
        const val KEY_USERNAME = "username"
        const val KEY_USERS = "users"
    }

    fun setLogin(isLoggedIn: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setUsername(username: String) {
        val editor = prefs.edit()
        editor.putString(KEY_USERNAME, username)
        editor.apply()
    }

    fun getUsername(): String? {
        return prefs.getString(KEY_USERNAME, null)
    }

    fun logout() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun registerUser(newUser: Usuario): Boolean {
        val users = getUsers()
        if (users.any { it.username == newUser.username }) {
            return false // El usuario ya existe
        }
        users.add(newUser)
        saveUsers(users)
        return true
    }

    fun validateUser(username: String, password: String): Boolean {
        val users = getUsers()
        return users.any { it.username == username && it.password == password }
    }

    private fun getUsers(): MutableList<Usuario> {
        val usersJson = prefs.getString(KEY_USERS, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<Usuario>>() {}.type
        return gson.fromJson(usersJson, type)
    }

    private fun saveUsers(users: MutableList<Usuario>) {
        val editor = prefs.edit()
        val usersJson = gson.toJson(users)
        editor.putString(KEY_USERS, usersJson)
        editor.apply()
    }
}
