package kz.sherua.nadoprodat.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable
import io.reactivex.Single
import kz.sherua.nadoprodat.model.dbentity.Sales
import kz.sherua.nadoprodat.model.dbentity.SalesWithDetailsAndProducts

@Dao
interface SalesDao {

    @Insert
    fun insertSale(sales: Sales) : Single<Long>

    @Transaction
    @Query("SELECT * FROM Sales")
    fun getSales(): List<SalesWithDetailsAndProducts>
}