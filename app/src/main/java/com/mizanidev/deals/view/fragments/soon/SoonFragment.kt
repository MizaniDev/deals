package com.mizanidev.deals.view.fragments.soon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mizanidev.deals.R
import com.mizanidev.deals.model.generalapi.GamesList
import com.mizanidev.deals.model.generalapi.GamesRequest
import com.mizanidev.deals.util.CToast
import com.mizanidev.deals.util.SharedPreferenceConstants
import com.mizanidev.deals.util.recyclerview.OnLoadMoreListener
import com.mizanidev.deals.util.recyclerview.RecyclerViewLoadMoreScroll
import com.mizanidev.deals.view.activity.HomeActivityAdapter
import com.mizanidev.deals.view.fragments.BaseFragment
import com.mizanidev.deals.view.fragments.soon.viewmodel.SoonViewModel
import com.mizanidev.deals.viewmodel.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class SoonFragment : BaseFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var loading: ProgressBar
    private lateinit var loadingRow: ProgressBar

    private lateinit var adapter: HomeActivityAdapter
    private lateinit var loadMoreItemsCells: ArrayList<GamesList?>
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    private lateinit var searchButton: FloatingActionButton

    private val viewModel: SoonViewModel by viewModel()

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
        loadingRow = view.findViewById(R.id.loading_row)
        searchButton = view.findViewById(R.id.floating_action_button)
        searchButton.setOnClickListener { showSearchGame() }
    }

    private fun setObservables(){
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when(it){
                is ViewState.ShowSoonGames -> showList(it.items)
                is ViewState.Loading -> showLoading()
                is ViewState.Loaded -> hideLoading()
                is ViewState.LoadingRecyclerView -> prepareRecyclerView()
                is ViewState.ShowMoreGames -> loadMoreGames(it.items)
                is ViewState.LoadedRecyclerView -> normalizeRecyclerView()
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

    private fun showList(list: GamesRequest?) {
        if(list!!.gameLists.isEmpty()) {
            val cToast = CToast(requireContext())
            cToast.showInfo(getString(R.string.no_results))
        }

        adapter = HomeActivityAdapter(requireContext(), list!!.gameLists)
        adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter

        nextPage = list.links?.nextLink

        setLayoutManager()
        setScrollListener()
    }

    private fun setLayoutManager() {
        mLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = mLayoutManager
        recyclerView.setHasFixedSize(true)
    }

    private fun setScrollListener() {
        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                if(nextPage != null){
                    viewModel.loadMoreItems(nextPage, requireContext())
                }
            }
        })

        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun prepareRecyclerView() {
        loadingRow.visibility = View.VISIBLE
        loadMoreItemsCells = ArrayList()

    }

    private fun loadMoreGames(gamesRequest: GamesRequest?) {
        nextPage = gamesRequest?.links?.nextLink
        gamesRequest?.gameLists?.let {
            loadMoreItemsCells.addAll(it)
        }
    }

    private fun normalizeRecyclerView() {
        loadingRow.visibility = View.GONE
        adapter.addMore(loadMoreItemsCells)
        scrollListener.loaded()
    }

    companion object {
        var nextPage: String? = null
    }
}