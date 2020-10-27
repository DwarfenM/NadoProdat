package kz.sherua.nadoprodat.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviDialogFragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.add_prop_dialog.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.presenter.AddPropPresenter
import kz.sherua.nadoprodat.state.AddPropDialogState
import kz.sherua.nadoprodat.view.AddPropDialogView

class AddPropDialog (private val itemAddTrigger: BehaviorSubject<Boolean>) :  MviDialogFragment<AddPropDialogView, AddPropPresenter>(),
    AddPropDialogView {

    companion object {

        const val TAG = "AddPropDialog"

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        itemAddTrigger.onNext(true)
    }

    override fun createPresenter() = AddPropPresenter(context!!)

    override fun addPropIntent(): Observable<String> {
        return RxView.clicks(btnAddPropName).map {
            etAddPropName.text.toString()
        }
    }

    override fun render(state: AddPropDialogState) {
        when(state) {
            is AddPropDialogState.itemAdded -> {
                dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_prop_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}