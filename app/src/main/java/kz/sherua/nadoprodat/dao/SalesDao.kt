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
    fun getSales(): Flowable<List<SalesWithDetailsAndProducts>>

    @Transaction
    @Query("SELECT * FROM Sales s where s.crDate > :startDate and s.crDate < :endDate")
    fun getSalesWithPeriod(startDate: Long, endDate: Long): Flowable<List<SalesWithDetailsAndProducts>>
}