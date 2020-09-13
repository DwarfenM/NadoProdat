package kz.sherua.nadoprodat.dao

import androidx.room.Dao
import androidx.room.Insert
import kz.sherua.nadoprodat.model.dbentity.Property

@Dao
interface PropertyDao {

    @Insert
    fun insertProperty(property: Property)
}