package com.mizanidev.deals.view.fragments.soon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mizanidev.deals.R
import com.mizanidev.deals.model.generalapi.GamesList
import com.mizanidev.deals.util.SharedPreferenceConstants
import com.mizanidev.deals.view.activity.HomeActivityAdapter
import com.mizanidev.deals.view.fragments.BaseFragment
import com.mizanidev.deals.viewmodel.DealsViewModel
import com.mizanidev.deals.viewmodel.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SoonFragment : BaseFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var loading: ProgressBar
    private val viewModel: DealsViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recycler_view_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(view)
        setObservables()
        makeRequest()
    }

    private fun initComponents(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        loading = view.findViewById(R.id.loading)
    }

    private fun setObservables(){
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when(it){
                is ViewState.ShowOnSale -> showList(it.items)
                is ViewState.Loading -> showLoading()
                is ViewState.Loaded -> hideLoading()
            }
        })
    }

    private fun makeRequest() {
        val currency = listener?.sharedPreference()?.stringConfig(SharedPreferenceConstants.CURRENCY)
        if(currency?.isEmpty() == true) {
            viewModel.requestSoonGames("USD", "en", "nintendo")
        } else {
            viewModel.requestSoonGames(currency!!, "en", "nintendo")
        }
    }

    private fun showLoading(){
        recyclerView.visibility = View.GONE
        loading.visibility = View.VISIBLE
    }

    private fun hideLoading(){
        loading.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun showList(list: List<GamesList>){
        recyclerView.adapter = HomeActivityAdapter(requireContext(), list)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    }
}