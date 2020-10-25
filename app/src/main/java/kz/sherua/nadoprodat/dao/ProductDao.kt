package kz.sherua.nadoprodat.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import kz.sherua.nadoprodat.model.dbentity.Product
import kz.sherua.nadoprodat.model.dbentity.ProductWithProps

@Dao
interface ProductDao {

    @Insert
    fun insertProduct(product: Product) : Single<Long>

    @Query("UPDATE product set count = case when count - :count >= 0 then count - :count else 0 end WHERE id = :productId")
    fun updateProduct(productId: Long, count: Int) : Completable

    @Transaction
    @Query("SELECT * FROM Product WHERE name like '%' || :nameLike || '%'")
    fun getFoundProducts(nameLike: String): Flowable<List<ProductWithProps>>

    @Transaction
    @Query("SELECT * FROM Product")
    fun getAllProducts(): Flowable<List<ProductWithProps>>

}