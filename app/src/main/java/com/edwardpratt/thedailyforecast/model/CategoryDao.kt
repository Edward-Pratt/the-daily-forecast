package com.edwardpratt.thedailyforecast.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


// Data Access Object (DAO) for the CategoryEntity
@Dao
interface CategoryDao {
    @Insert
    suspend fun insertCategory(category: CategoryEntity)

    @Query("SELECT * FROM categories WHERE categoryType = :type")
    fun getCategoriesByType(type: String): LiveData<List<CategoryEntity>>

    @Query("SELECT * FROM categories")
    fun getAllCategories(): LiveData<List<CategoryEntity>>

    @Query("DELETE FROM categories WHERE name = :name AND categoryType = :type")
    suspend fun deleteCategoryByName(name: String, type: String)
}