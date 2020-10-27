package kz.sherua.nadoprodat.presenter

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.sherua.nadoprodat.database.NadoProdatDatabase
import kz.sherua.nadoprodat.model.dbentity.Property
import kz.sherua.nadoprodat.state.AddPropDialogState
import kz.sherua.nadoprodat.view.AddPropDialogView

class AddPropPresenter(val ctx: Context) : MviBasePresenter<AddPropDialogView, AddPropDialogState>() {

    private val npDb = NadoProdatDatabase.getInstance(ctx)

    override fun bindIntents() {
        val addPropIntent: Observable<AddPropDialogState> =
            intent(AddPropDialogView::addPropIntent).flatMap {
                val prop = Property(name = it, crDate = System.currentTimeMillis(), upDate = System.currentTimeMillis())
                npDb.propertyDao().insertProperty(prop).andThen(
                    Observable.just(AddPropDialogState.itemAdded)
                ).subscribeOn(Schedulers.io())
            }

        val allIntents = addPropIntent.observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents, AddPropDialogView::render)
    }
}