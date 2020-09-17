package kz.sherua.nadoprodat.presenter

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kz.sherua.nadoprodat.state.AddItemState
import kz.sherua.nadoprodat.utils.PreferenceHelper.addItemToSP
import kz.sherua.nadoprodat.view.AddItemView

class AddItemPresenter(val ctx: Context) : MviBasePresenter<AddItemView, AddItemState>() {


    override fun bindIntents() {
        val addCountIntent: Observable<AddItemState> =
            intent(AddItemView::addCountIntent).map {
                AddItemState.AddCount(it + 1)
            }

        val removeCountIntent: Observable<AddItemState> =
            intent(AddItemView::removeCountIntent).map {
                AddItemState.RemoveCount(it - 1)
            }

        val addItemIntent: Observable<AddItemState> =
            intent(AddItemView::addItemIntent).map {
                addItemToSP(it, ctx)
                AddItemState.AddItem
            }

        val allIntents = Observable.merge(addCountIntent, removeCountIntent, addItemIntent).observeOn(
            AndroidSchedulers.mainThread())

        subscribeViewState(allIntents, AddItemView::render)
    }




}