package com.mizanidev.deals.model.generic

data class KnowsBugs (
    val idBug: Int = 0,
    val title: String = "",
    val description: String = "",
    val solved: Boolean = false,
    val versionSolved: String = ""
)