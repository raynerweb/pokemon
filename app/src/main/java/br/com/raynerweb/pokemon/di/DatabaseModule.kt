package br.com.raynerweb.pokemon.di

import android.content.Context
import androidx.room.Room
import br.com.raynerweb.pokemon.repository.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "PokemonDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePokemonDao(database: AppDatabase) = database.pokemonDao()

    @Singleton
    @Provides
    fun provideTypeDao(database: AppDatabase) = database.typeDao()

    @Singleton
    @Provides
    fun providePokemonTypeDao(database: AppDatabase) = database.pokemonTypeDao()

}
