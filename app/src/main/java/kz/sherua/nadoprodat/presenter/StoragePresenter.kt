package kz.sherua.nadoprodat.presenter

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.sherua.nadoprodat.database.NadoProdatDatabase
import kz.sherua.nadoprodat.state.StorageState
import kz.sherua.nadoprodat.view.StorageView

class StoragePresenter(ctx: Context) : MviBasePresenter<StorageView, StorageState>() {

    private val npDb = NadoProdatDatabase.getInstance(ctx)

    override fun bindIntents() {
        val checkSalesConditionIntent: Observable<StorageState> =
            intent(StorageView::checkProductConditionIntent).flatMap {
                npDb.productDao().getAllProducts().toObservable().map {
                    if (it.isNotEmpty()) {
                        StorageState.GetAllProducts(it)
                    } else {
                        StorageState.NoProducts
                    }
                }.subscribeOn(Schedulers.io())
            }

        val allIntents = checkSalesConditionIntent.observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents, StorageView::render)
    }
}