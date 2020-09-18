package kz.sherua.nadoprodat.presenter

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.sherua.nadoprodat.database.NadoProdatDatabase
import kz.sherua.nadoprodat.state.AddItemState
import kz.sherua.nadoprodat.utils.PreferenceHelper.addItemToSP
import kz.sherua.nadoprodat.view.AddItemView

class AddItemPresenter(val ctx: Context) : MviBasePresenter<AddItemView, AddItemState>() {

    private val npDb = NadoProdatDatabase.getInstance(ctx)

    override fun bindIntents() {
        val addCountIntent: Observable<AddItemState> =
            intent(AddItemView::addCountIntent).map {
                AddItemState.AddCount(it + 1)
            }

        val removeCountIntent: Observable<AddItemState> =
            intent(AddItemView::removeCountIntent).map {
                AddItemState.RemoveCount(it - 1)
            }

        val addItemIntent: Observable<AddItemState.AddItem>? =
            intent(AddItemView::addItemIntent).flatMap { product ->
                npDb.productDao().insertProduct(product)
                    .flatMap {
                        product.id = it
                        addItemToSP(product, ctx)
                        Single.just(AddItemState.AddItem)
                    }.toObservable()
                    .subscribeOn(Schedulers.io())
            }

        val allIntents = Observable.merge(addCountIntent, removeCountIntent, addItemIntent).observeOn(
            AndroidSchedulers.mainThread())

        subscribeViewState(allIntents, AddItemView::render)
    }




}