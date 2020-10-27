package kz.sherua.nadoprodat.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_properties_bottom_dialog.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.adapter.PropertyNamesAdapter
import kz.sherua.nadoprodat.model.dbentity.Property
import kz.sherua.nadoprodat.presenter.PropertiesBottomDialogPresenter
import kz.sherua.nadoprodat.state.PropertiesBottomDialogState
import kz.sherua.nadoprodat.view.PropertiesBottomDialogView

class PropertiesBottomDialogFragment(private val itemAddTrigger: BehaviorSubject<List<Property>>) :
    BaseBottomDialogMVI<PropertiesBottomDialogView, PropertiesBottomDialogState, PropertiesBottomDialogPresenter>(), PropertiesBottomDialogView {

    private lateinit var itemAdded: BehaviorSubject<Boolean>
    private lateinit var itemsAdapter: PropertyNamesAdapter

    companion object {

        const val TAG = "PropertiesBottomDialogFragment"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemAdded = BehaviorSubject.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_properties_bottom_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemsAdapter = PropertyNamesAdapter(context!!)
        rvProperties.adapter = itemsAdapter
        rvProperties.layoutManager = GridLayoutManager(context!!, 1)
        tvAddProp.setOnClickListener {
            val dialog = AddPropDialog(itemAdded)
            dialog.show(activity?.supportFragmentManager!!, AddItemDialog.TAG)
            activity?.supportFragmentManager!!.executePendingTransactions()
        }
        tvAddAll.setOnClickListener {
            Log.d("tag", "data is: " + itemsAdapter.getSelected())
            itemAddTrigger.onNext(itemsAdapter.getSelected())
            dismiss()
        }
        itemAdded.onNext(true)
    }

    override fun createPresenter() = PropertiesBottomDialogPresenter(context!!)
    override fun getAllPropsIntent(): Observable<Boolean> {
        return itemAdded
    }

    override fun render(state: PropertiesBottomDialogState) {
        when(state) {
            is PropertiesBottomDialogState.GetAllPropNames -> {
                itemsAdapter.reassignItems(state.properties.toMutableList())
            }
        }
    }

}