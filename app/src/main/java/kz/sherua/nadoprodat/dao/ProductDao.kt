package kz.sherua.nadoprodat.dao

import androidx.room.Dao
import androidx.room.Insert
import kz.sherua.nadoprodat.model.dbentity.Product

@Dao
interface ProductDao {

    @Insert
    fun insertProduct(product: Product)
}