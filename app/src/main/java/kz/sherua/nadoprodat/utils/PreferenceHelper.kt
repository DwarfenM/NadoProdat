package kz.sherua.nadoprodat.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kz.sherua.nadoprodat.model.BasketModel

object PreferenceHelper {

    fun defaultPrefs(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    fun customPrefs(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }


    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }


    inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    fun getBasket(ctx: Context) : MutableList<BasketModel> {
        val gson = Gson()
        val prefs = customPrefs(ctx, Constants.NP_PREF_NAME)
        val turnsType = object : TypeToken<List<BasketModel>>() {}.type
        val json: String? = prefs[Constants.BASKET]
        var basketList: MutableList<BasketModel>? = gson.fromJson(json, turnsType)
        if (basketList == null) {
            basketList = arrayListOf()
        }
        return basketList
    }

    fun addItemToSP(model: BasketModel, ctx: Context) {
        val gson = Gson()
        val prefs = customPrefs(ctx, Constants.NP_PREF_NAME)
        val turnsType = object : TypeToken<List<BasketModel>>() {}.type
        val json: String? = prefs[Constants.BASKET]
        var basketList = getBasket(ctx)
        Log.d("AddItemPresenter", "list : $basketList")
        basketList.add(model)
        prefs[Constants.BASKET] = gson.toJson(basketList)
    }

    fun clearAuthData(context: Context?){
        val prefs = customPrefs(context!!, Constants.NP_PREF_NAME)
        prefs[Constants.BASKET] = null
        prefs[Constants.AUTH_REFRESH_TOKEN] = null
    }
}