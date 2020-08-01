package com.mizanidev.deals.modules

import com.mizanidev.deals.viewmodel.DealsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    viewModel {
        DealsViewModel()
    }

}
