package kz.sherua.nadoprodat.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kz.sherua.nadoprodat.dao.*
import kz.sherua.nadoprodat.model.dbentity.*

@Database(entities = [Group::class, Product::class, PropertyValues::class, Property::class, SaleDetails::class, Sales::class, SubGroup::class, Type::class],version = 1)
abstract class NadoProdatDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun propertyDao(): PropertyDao

    abstract fun propertyValuesDao(): PropertyValuesDao

    abstract fun salesDao(): SalesDao

    abstract fun saleDetailsDao(): SaleDetailsDao

    companion object{
        private var INSTANCE: NadoProdatDatabase? = null
        fun getInstance(context: Context): NadoProdatDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    NadoProdatDatabase::class.java,
                    "roomdb")
                    .build()
            }

            return INSTANCE as NadoProdatDatabase
        }
    }
}