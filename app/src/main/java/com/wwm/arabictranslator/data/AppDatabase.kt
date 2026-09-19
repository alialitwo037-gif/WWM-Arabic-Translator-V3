package com.wwm.arabictranslator.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

@Entity(tableName = "translation_memory")
data class TranslationEntity(
    @PrimaryKey val originalText: String,
    val translatedText: String
)

@Entity(tableName = "glossary")
data class GlossaryEntity(
    @PrimaryKey val sourceTerm: String,
    val targetTerm: String
)

@Dao
interface TranslationDao {
    @Query("SELECT translatedText FROM translation_memory WHERE originalText = :original")
    suspend fun getTranslation(original: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslation(entity: TranslationEntity)
}

@Dao
interface GlossaryDao {
    @Query("SELECT * FROM glossary")
    suspend fun getAllTerms(): List<GlossaryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTerm(entity: GlossaryEntity)
}

@Database(entities = [TranslationEntity::class, GlossaryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun translationDao(): TranslationDao
    abstract fun glossaryDao(): GlossaryDao
}
