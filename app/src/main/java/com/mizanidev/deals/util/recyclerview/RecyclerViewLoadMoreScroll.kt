package com.mizanidev.deals.util.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewLoadMoreScroll(layoutManager: LinearLayoutManager)
    : RecyclerView.OnScrollListener() {

    private var visibleThreshold = 5
    private lateinit var onLoadMoreListener: OnLoadMoreListener
    private var isLoading: Boolean = false
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var mLayoutManager: RecyclerView.LayoutManager = layoutManager

    fun loaded() {
        isLoading = false
    }

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if(dy <= 0) return

        totalItemCount = mLayoutManager.itemCount
        lastVisibleItem = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if(!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
            onLoadMoreListener.onLoadMore()
            isLoading = true
        }

    }

}