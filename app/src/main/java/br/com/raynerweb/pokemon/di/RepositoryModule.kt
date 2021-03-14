package br.com.raynerweb.pokemon.di

import br.com.raynerweb.pokemon.repository.PokemonRepository
import br.com.raynerweb.pokemon.repository.TrainerRepository
import br.com.raynerweb.pokemon.repository.impl.PokemonRepositoryImpl
import br.com.raynerweb.pokemon.repository.impl.TrainerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providePokemonRepository(pokemonRepositoryImpl: PokemonRepositoryImpl): PokemonRepository

    @Binds
    abstract fun provideTrainerRepository(trainerRepositoryImpl: TrainerRepositoryImpl): TrainerRepository
}