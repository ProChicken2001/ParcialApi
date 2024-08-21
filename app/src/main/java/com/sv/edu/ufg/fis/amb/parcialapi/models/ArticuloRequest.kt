package com.sv.edu.ufg.fis.amb.parcialapi.models

data class ArticuloRequest(
    var q: String = "news",
    val from: String? = null,
    val to: String? = null,
    var language: String? = null,
)
