package kz.sherua.nadoprodat.dao

import androidx.room.Dao
import androidx.room.Insert
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import kz.sherua.nadoprodat.model.dbentity.SaleDetails

@Dao
interface SaleDetailsDao {

    @Insert
    fun insertAllSaleDetails(saleDetails: List<SaleDetails>) : Completable
}