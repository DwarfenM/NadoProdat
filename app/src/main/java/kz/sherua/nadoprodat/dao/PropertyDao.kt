package kz.sherua.nadoprodat.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Flowable
import kz.sherua.nadoprodat.model.dbentity.Property

@Dao
interface PropertyDao {

    @Insert
    fun insertProperty(property: Property) : Completable

    @Transaction
    @Query("SELECT * FROM Property")
    fun getAllProps(): Flowable<List<Property>>
}