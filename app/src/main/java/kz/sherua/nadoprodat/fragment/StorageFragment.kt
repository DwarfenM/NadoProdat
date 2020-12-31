package kz.sherua.nadoprodat.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding2.widget.RxAdapterView
import com.jakewharton.rxbinding2.widget.RxSearchView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_storage.*
import kotlinx.android.synthetic.main.fragment_storage.hasItemLayout
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.adapter.StorageItemsAdapter
import kz.sherua.nadoprodat.model.StorageSortModel
import kz.sherua.nadoprodat.presenter.StoragePresenter
import kz.sherua.nadoprodat.state.StorageState
import kz.sherua.nadoprodat.view.StorageView
import java.text.SimpleDateFormat

class StorageFragment : MviFragment<StorageView, StoragePresenter>(), StorageView {

    private lateinit var itemsAdapter: StorageItemsAdapter
    private lateinit var checkConditionTrigger: BehaviorSubject<Boolean>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.appBarMain?.visibility = View.VISIBLE
        activity?.tvHeaderTab?.visibility = View.VISIBLE
        activity?.tvHeaderTab?.text = "Склад"
        checkConditionTrigger = BehaviorSubject.create()
    }

    override fun onResume() {
        checkConditionTrigger.onNext(true)
        activity?.appBarMain?.visibility = View.VISIBLE
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_storage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemsAdapter = StorageItemsAdapter(context!!)
        appBarStorage.visibility = View.GONE
        rvStorage.adapter = itemsAdapter
        rvStorage.layoutManager = GridLayoutManager(context!!, 1)
        toolbarStorage.setNavigationIcon(R.drawable.ic_back_button)
        toolbarStorage.setNavigationOnClickListener{
            activity?.appBarMain?.visibility = View.VISIBLE
            appBarStorage.visibility = View.GONE
        }
        activity?.searchView?.setOnSearchClickListener {
            activity?.searchView?.onActionViewCollapsed()
            activity?.appBarMain?.visibility = View.GONE
            emptyStorage.visibility = View.GONE
            hasItemLayout.visibility = View.GONE
            appBarStorage.visibility = View.VISIBLE
            searchViewStorage.onActionViewExpanded()
        }
        btnCreateItem.setOnClickListener {
            val navController =
                Navigation.findNavController((context as FragmentActivity).findViewById(R.id.nav_host_fragment))
            val action = StorageFragmentDirections.actionStorageFragmentToSingleProductFragment("", true)
            navController.navigate(action)
        }
    }

    override fun createPresenter() = StoragePresenter(context!!)

    override fun checkProductConditionIntent(): Observable<Boolean> {
        return checkConditionTrigger
    }

    override fun searchProductsIntent(): Observable<String> {
        return RxSearchView.queryTextChanges(searchViewStorage)
            .map (CharSequence::toString)
    }

    override fun sortStorage(): Observable<StorageSortModel> {
        return RxAdapterView.itemSelections(spinnerSort).map {
           StorageSortModel(resources.getStringArray(R.array.storage_sort_array)[it].toString(), itemsAdapter.getItems())
        }
    }


    override fun render(state: StorageState) {
        when(state) {
            is StorageState.NoProducts -> {
                emptyStorage.visibility = View.VISIBLE
                hasItemLayout.visibility = View.GONE
            }
            is StorageState.GetAllProducts -> {
                emptyStorage.visibility = View.GONE
                hasItemLayout.visibility = View.VISIBLE
                itemsAdapter.addItems(state.productList.toMutableList())
            }
        }
    }
}