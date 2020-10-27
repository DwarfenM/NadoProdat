package kz.sherua.nadoprodat

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kz.sherua.nadoprodat.dao.*
import kz.sherua.nadoprodat.database.NadoProdatDatabase
import kz.sherua.nadoprodat.model.dbentity.*
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var productDao: ProductDao
    private lateinit var propertyDao: PropertyDao
    private lateinit var propertyValuesDao: PropertyValuesDao
    private lateinit var salesDetailsDao: SaleDetailsDao
    private lateinit var salesDao: SalesDao
    private lateinit var nadoProdatDatabase: NadoProdatDatabase

    @Before
    fun createDatabase() {
        val context  = ApplicationProvider.getApplicationContext<Context>()
        nadoProdatDatabase = Room.inMemoryDatabaseBuilder(context,NadoProdatDatabase::class.java).build()
        productDao = nadoProdatDatabase.productDao()
        propertyDao = nadoProdatDatabase.propertyDao()
        propertyValuesDao = nadoProdatDatabase.propertyValuesDao()
        salesDetailsDao = nadoProdatDatabase.saleDetailsDao()
        salesDao = nadoProdatDatabase.salesDao()
    }

    @After
    fun closeDatabase() {
        nadoProdatDatabase.close()
    }

    @Test
    fun testNadoProdatRelationships() {
        val product1 = Product(0,"test",50,40.5,0,50.5,0,0,0,0,true,"fdfd", 0, 0)
        productDao.insertProduct(product1).subscribe()

        val property1 = Property(0,"cvet", 0, 0)
        val property2 = Property(1,"ves", 0, 0)
        propertyDao.insertProperty(property1)
        propertyDao.insertProperty(property2)

        val propertyValues1 = PropertyValues(0,0, "krasnii")
        val propertyValues2 = PropertyValues(0,1, "60kg")
        propertyValuesDao.insertPropertyValues(propertyValues1)
        propertyValuesDao.insertPropertyValues(propertyValues2)

        var productsWithProp = propertyValuesDao.getAllProductsWithProperty(0)
        Log.d("testDb", "values: $productsWithProp")

        val sales = Sales(id=0,salesPrice = 10.0, crDate = 0)
        salesDao.insertSale(sales).subscribe()

        val salesDetail = SaleDetails(salesId = 0,productId = 0,productSalesPrice = 0.0,crDate = 0,productCount = 0)
        salesDetailsDao.insertAllSaleDetails(arrayListOf(salesDetail)).subscribe()

        Log.d("testDbSales", "sales values: ${salesDao.getSales()}")


    }


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("kz.sherua.nadoprodat", appContext.packageName)
    }
}