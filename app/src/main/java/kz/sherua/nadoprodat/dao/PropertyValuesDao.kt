package kz.sherua.nadoprodat.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Single
import kz.sherua.nadoprodat.model.dbentity.PropertyValuesWithProps
import kz.sherua.nadoprodat.model.dbentity.PropertyValues

@Dao
interface PropertyValuesDao {

    @Insert
    fun insertPropertyValues(propertyValues: PropertyValues) : Single<Long>

    @Transaction
    @Query("SELECT * FROM PropertyValues WHERE productId = :productId")
    fun getAllProductsWithProperty(productId: Long): List<PropertyValuesWithProps>
}