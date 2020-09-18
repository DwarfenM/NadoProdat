package kz.sherua.nadoprodat.dao

import androidx.room.Dao
import androidx.room.Insert
import io.reactivex.Single
import kz.sherua.nadoprodat.model.dbentity.Sales

@Dao
interface SalesDao {

    @Insert
    fun insertSale(sales: Sales) : Single<Long>
}