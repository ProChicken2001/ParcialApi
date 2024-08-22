package com.sv.edu.ufg.fis.amb.parcialapi

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.sv.edu.ufg.fis.amb.parcialapi.configs.RetrofitConfig
import com.sv.edu.ufg.fis.amb.parcialapi.models.ArticuloRequest
import com.sv.edu.ufg.fis.amb.parcialapi.models.ArticuloResponse
import com.sv.edu.ufg.fis.amb.parcialapi.pages.FilterPage
import com.sv.edu.ufg.fis.amb.parcialapi.pages.MainPage
import com.sv.edu.ufg.fis.amb.parcialapi.routes.ART_FILTER_ARGUMENT_KEY
import com.sv.edu.ufg.fis.amb.parcialapi.routes.Route
import com.sv.edu.ufg.fis.amb.parcialapi.ui.theme.ParcialApiTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.logging.Filter

@Composable
fun SetupNavGraph(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Route.MainRoutePage.route
    ){
        composable(
            route = Route.MainRoutePage.route
        ){
            val context = LocalContext.current

            val filterDataJson = it.arguments?.getString(ART_FILTER_ARGUMENT_KEY)
            val filterRequest = Gson().fromJson(filterDataJson, ArticuloRequest::class.java)

            var request by remember {
                mutableStateOf(ArticuloResponse(emptyList()))
            }

            if(filterRequest != null){

                LaunchedEffect(Unit) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val response = RetrofitConfig
                            .api
                            .getNews(
                                filterRequest.q,
                                filterRequest.from,
                                filterRequest.to,
                                filterRequest.language,
                                getString(context, R.string.apiKey)
                            )

                        if(response.isSuccessful){
                            response.body()?.let {
                                request = it
                            }
                        }else{
                            Log.e("NO FOUND", "NO HAY NADA -----------------------------")
                        }
                    }
                }
            }else{
                LaunchedEffect(Unit) {

                    CoroutineScope(Dispatchers.IO).launch {
                        val response = RetrofitConfig
                            .api
                            .getNews(
                                q = "news",
                                apiKey = getString(context, R.string.apiKey)
                            )

                        if(response.isSuccessful){
                            response.body()?.let {
                                request = it
                            }
                        }else{
                            Log.e("NO FOUND", "NO HAY NADA -----------------------------")
                        }
                    }
                }
            }

            ParcialApiTheme(
                dynamicColor = false
            ) {
                MainPage(request, navController)
            }


        }

        composable(
            route = Route.FilterRoutePage.route
        ){
            ParcialApiTheme(
                dynamicColor = false
            ) {
                FilterPage()
            }
        }


    }
}
