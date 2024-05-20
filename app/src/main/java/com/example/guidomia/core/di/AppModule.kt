package com.example.guidomia.core.di

import android.content.Context
import com.example.guidomia.core.preferences.DataStorePref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun getDataStorePref(@ApplicationContext context: Context): DataStorePref {
        return DataStorePref.DataStorePrefImpl(context)
    }

}