package com.example.colorpickerfix

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class MyDataStoreRepository private constructor(private val dataStore: DataStore<Preferences>) {
    val redSeekBarProgress: Flow<String> = dataStore.data.map { prefs ->
        prefs[RED_SEEKBAR_KEY] ?: ""
    }.distinctUntilChanged()

    val blueSeekBarProgress: Flow<String> = dataStore.data.map { prefs ->
        prefs[BLUE_SEEKBAR_KEY] ?: ""
    }.distinctUntilChanged()

    val greenSeekBarProgress: Flow<String> = dataStore.data.map { prefs ->
        prefs[GREEN_SEEKBAR_KEY] ?: ""
    }.distinctUntilChanged()

    val redSwitch: Flow<String> = dataStore.data.map { prefs ->
        prefs[RED_SWITCH_KEY] ?: ""
    }.distinctUntilChanged()

    val blueSwitch: Flow<String> = dataStore.data.map { prefs ->
        prefs[BLUE_SWITCH_KEY] ?: ""
    }.distinctUntilChanged()

    val greenSwitch: Flow<String> = dataStore.data.map { prefs ->
        prefs[GREEN_SWITCH_KEY] ?: ""
    }.distinctUntilChanged()

    val redText: Flow<String> = dataStore.data.map { prefs ->
        prefs[RED_TEXT_KEY] ?: ""
    }.distinctUntilChanged()

    val blueText: Flow<String> = dataStore.data.map { prefs ->
        prefs[BLUE_TEXT_KEY] ?: ""
    }.distinctUntilChanged()

    val greenText: Flow<String> = dataStore.data.map { prefs ->
        prefs[GREEN_TEXT_KEY] ?: ""
    }.distinctUntilChanged()
    private suspend fun saveValue(value: String, key: Preferences.Key<String>) {
        dataStore.edit { prefs ->
            prefs[key] = value
        }
    }
    suspend fun saveInput(value: String, index: Int) {
        val key: Preferences.Key<String> = when (index) {
            1 -> RED_SEEKBAR_KEY
            2 -> GREEN_SEEKBAR_KEY
            3 -> BLUE_SEEKBAR_KEY
            4 -> RED_SWITCH_KEY
            5 -> GREEN_SWITCH_KEY
            6 -> BLUE_SWITCH_KEY
            7 -> RED_TEXT_KEY
            8 -> BLUE_TEXT_KEY
            9 -> GREEN_TEXT_KEY

            else -> { throw NoSuchFieldException("Invalidd input index: $index")}
        }
        this.saveValue(value, key)
    }

//    suspend fun loadInput(value: String, index: Int) {
//        val key: Preferences.Key<String> = when (index) {
//            1 -> RED_SEEKBAR_KEY
//            2 -> GREEN_SEEKBAR_KEY
//            3 -> BLUE_SEEKBAR_KEY
//            4 -> RED_SWITCH_KEY
//            5 -> GREEN_SWITCH_KEY
//            6 -> BLUE_SWITCH_KEY
//
//            else -> { throw NoSuchFieldException("Invalid input index: $index")}
//        }
//        this.saveValue(value, key)
//    }
    companion object {
        private const val DATA_STORE_FILE_NAME = "status"
        private val RED_SEEKBAR_KEY = stringPreferencesKey("redSeekbar")
        private val GREEN_SEEKBAR_KEY = stringPreferencesKey("greenSeekbar")
        private val BLUE_SEEKBAR_KEY = stringPreferencesKey("blueSeekbar")
        private val RED_SWITCH_KEY = stringPreferencesKey("redSwitch")
        private val GREEN_SWITCH_KEY = stringPreferencesKey("blueSwitch")
        private val BLUE_SWITCH_KEY = stringPreferencesKey("greenSwitch")
        private val RED_TEXT_KEY = stringPreferencesKey("redText")
        private val BLUE_TEXT_KEY = stringPreferencesKey("blueText")
        private val GREEN_TEXT_KEY = stringPreferencesKey("greenText")

        private var INSTANCE: MyDataStoreRepository? = null
        fun get(): MyDataStoreRepository {
            return INSTANCE ?: throw IllegalStateException("Trying to get the singleton INSTANCE of type MyDataStoreRepository before it has been initialized")
        }
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                val dataStore = PreferenceDataStoreFactory.create {
                    context.preferencesDataStoreFile(DATA_STORE_FILE_NAME)
                }
                INSTANCE = MyDataStoreRepository(dataStore)
            }
        }
    }
}