package com.mizanidev.deals.modules

import com.mizanidev.deals.view.fragments.onsale.viewmodel.OnSaleViewModel
import com.mizanidev.deals.view.fragments.releases.viewmodel.ReleasesViewModel
import com.mizanidev.deals.view.fragments.soon.viewmodel.SoonViewModel
import com.mizanidev.deals.viewmodel.DealsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    viewModel {
        DealsViewModel(get())
    }

    viewModel { OnSaleViewModel() }
    viewModel { ReleasesViewModel() }
    viewModel { SoonViewModel() }
}
