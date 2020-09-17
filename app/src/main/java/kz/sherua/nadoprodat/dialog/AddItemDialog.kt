package kz.sherua.nadoprodat.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.hannesdorfmann.mosby3.mvi.MviDialogFragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.add_item_dialog.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.model.BasketModel
import kz.sherua.nadoprodat.presenter.AddItemPresenter
import kz.sherua.nadoprodat.state.AddItemState
import kz.sherua.nadoprodat.view.AddItemView

class AddItemDialog :  MviDialogFragment<AddItemView,AddItemPresenter>(), AddItemView{

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

    override fun addItemIntent(): Observable<BasketModel> {
        return RxView.clicks(btnAddItemAddItem).map {
            BasketModel(etAddItemItemName.text.toString(),tvAddItemCount.text.toString().toLong(),etAddItemPrice.text.toString().toLong(),null)
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

            }
        }
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