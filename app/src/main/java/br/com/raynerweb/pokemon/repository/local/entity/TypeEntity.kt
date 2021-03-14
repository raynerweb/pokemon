package br.com.raynerweb.pokemon.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "type")
data class TypeEntity(
    @PrimaryKey(autoGenerate = true)
    val typeId: Long = 0,
    val image: String,
    val name: String,
)