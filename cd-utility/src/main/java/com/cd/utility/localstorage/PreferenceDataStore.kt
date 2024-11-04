package com.cd.utility.localstorage

import android.content.Context
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PreferenceDataStore(
    context: Context,
    name: String = "delivery_app",
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>? = null,
    produceMigrations: (Context) -> List<DataMigration<Preferences>> = { listOf() },
    scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
) : IPreferenceDataStoreAPI {

    val Context.datastore: DataStore<Preferences> by preferencesDataStore(
        name = name,
        corruptionHandler = corruptionHandler,
        produceMigrations = produceMigrations,
        scope = scope
    )
    val dataSource = context.datastore


    override suspend fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T):
            Flow<T> = flow {
        val result = dataSource.data.map {
            it[key] ?: defaultValue
        }.first()
        emit(result)
    }

    override suspend fun <T> getFirstPreference(key: Preferences.Key<T>, defaultValue: T):
            T = dataSource.data.first()[key] ?: defaultValue

    override suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
        dataSource.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun <T> removePreference(key: Preferences.Key<T>) {
        dataSource.edit { preferences ->
            preferences.remove(key)
        }
    }

    override suspend fun <T> clearAllPreference() {
        dataSource.edit { preferences ->
            preferences.clear()
        }
    }

}