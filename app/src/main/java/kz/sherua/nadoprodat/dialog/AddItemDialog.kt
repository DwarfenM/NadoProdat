package kz.sherua.nadoprodat.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.hannesdorfmann.mosby3.mvi.MviDialogFragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.add_item_dialog.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.model.BasketModel
import kz.sherua.nadoprodat.model.dbentity.Product
import kz.sherua.nadoprodat.presenter.AddItemPresenter
import kz.sherua.nadoprodat.state.AddItemState
import kz.sherua.nadoprodat.view.AddItemView

class AddItemDialog(val itemAddTrigger: BehaviorSubject<Boolean>) :  MviDialogFragment<AddItemView,AddItemPresenter>(), AddItemView{

    companion object {

        const val TAG = "AddItemDialog"

    }

    override fun createPresenter() = AddItemPresenter(context!!)

    private var count = 0

    override fun addCountIntent(): Observable<Int> {
        return RxView.clicks(btnAddItemAddCount).map {
            count
        }
    }

    override fun removeCountIntent(): Observable<Int> {
        return RxView.clicks(btnAddItemMinusCount).map {
            count
        }
    }

    override fun addItemIntent(): Observable<Product> {
        return RxView.clicks(btnAddItemAddItem).map {
            Product(name = etAddItemItemName.text.toString(), count = tvAddItemCount.text.toString().toInt(), salesPrice = etAddItemPrice.text.toString().toDouble(), isFlex = true)
        }
    }

    override fun render(state: AddItemState) {
        when(state) {
            is AddItemState.AddCount -> {
                count = state.count
                tvAddItemCount.text = state.count.toString()
            }
            is AddItemState.RemoveCount -> {
                count = state.count
                tvAddItemCount.text = state.count.toString()
            }
            is AddItemState.AddItem -> {
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        itemAddTrigger.onNext(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_item_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}