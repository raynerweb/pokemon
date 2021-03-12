package br.com.raynerweb.pokemon.di

import br.com.raynerweb.pokemon.repository.service.PokemonService
import br.com.raynerweb.pokemon.repository.service.PokemonTypeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BackendModule {

    @Provides
    @Singleton
    fun pokemonService(retrofit: Retrofit) = retrofit.create(PokemonService::class.java)

    @Provides
    @Singleton
    fun pokemonTypeService(retrofit: Retrofit) = retrofit.create(PokemonTypeService::class.java)

}