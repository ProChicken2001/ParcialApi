package com.sv.edu.ufg.fis.amb.parcialapi.routes

const val ROOT_MAIN_PAGE = "main_page"
const val ART_FILTER_ARGUMENT_KEY = "art_filter"

const val ROOT_FILTER_PAGE = "filter_page"

sealed class Route(val route: String) {
    object MainRoutePage: Route(route = "${ROOT_MAIN_PAGE}/{${ART_FILTER_ARGUMENT_KEY}}")
    object FilterRoutePage: Route(route = ROOT_FILTER_PAGE)
}