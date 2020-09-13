package kz.sherua.nadoprodat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kz.sherua.nadoprodat.dao.ProductDao
import kz.sherua.nadoprodat.dao.PropertyDao
import kz.sherua.nadoprodat.dao.PropertyValuesDao
import kz.sherua.nadoprodat.model.dbentity.*

@Database(entities = [Group::class, Product::class, PropertyValues::class, Property::class, SaleDetails::class, Sales::class, SubGroup::class, Type::class],version = 1)
abstract class NadoProdatDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun propertyDao(): PropertyDao

    abstract fun propertyValuesDao(): PropertyValuesDao
}