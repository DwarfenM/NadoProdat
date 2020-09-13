package kz.sherua.nadoprodat.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kz.sherua.nadoprodat.model.dbentity.ProductWithProperty
import kz.sherua.nadoprodat.model.dbentity.PropertyValues

@Dao
interface PropertyValuesDao {

    @Insert
    fun insertPropertyValues(propertyValues: PropertyValues)

    @Transaction
    @Query("SELECT * FROM PropertyValues WHERE productId = :productId")
    fun getAllProductsWithProperty(productId: Long): List<ProductWithProperty>
}