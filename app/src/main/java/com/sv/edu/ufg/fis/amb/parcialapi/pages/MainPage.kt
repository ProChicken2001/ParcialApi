package com.sv.edu.ufg.fis.amb.parcialapi.pages

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.sv.edu.ufg.fis.amb.parcialapi.R
import com.sv.edu.ufg.fis.amb.parcialapi.configs.RetrofitConfig
import com.sv.edu.ufg.fis.amb.parcialapi.models.Articulo
import com.sv.edu.ufg.fis.amb.parcialapi.models.ArticuloRequest
import com.sv.edu.ufg.fis.amb.parcialapi.models.ArticuloResponse
import com.sv.edu.ufg.fis.amb.parcialapi.routes.ROOT_FILTER_PAGE
import com.sv.edu.ufg.fis.amb.parcialapi.routes.Route
import com.sv.edu.ufg.fis.amb.parcialapi.ui.theme.ParcialApiTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    response: ArticuloResponse?,
    request: ArticuloRequest?,
    navController: NavHostController
){
    val context = LocalContext.current
    val pullState = rememberPullToRefreshState()
    var newResponse by remember {
        mutableStateOf<ArticuloResponse?>(null)
    }

    var articulos by remember {
        mutableStateOf(response?.articles ?: emptyList())
    }

    LaunchedEffect(response) {
        articulos = response?.articles ?: emptyList()
    }

    LaunchedEffect(Unit) {
        while (true){
            val apiResponse = withContext(Dispatchers.IO){
                delay(60_000)
                if(request != null){
                    RetrofitConfig.api
                        .getNews(
                            q = request.q,
                            from = request.from,
                            to = request.to,
                            language = if(request.language == "") "es" else request.language,
                            apiKey = getString(context, R.string.apiKey)
                        )
                }else{
                    RetrofitConfig.api
                        .getNews(
                            q = "news",
                            language = "es",
                            apiKey = getString(context, R.string.apiKey)
                        )
                }
            }

            if(apiResponse.isSuccessful){
                apiResponse.body()?.let {
                    newResponse = it
                }

                articulos = newResponse?.articles ?: emptyList()
            }

            Toast.makeText(context, "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show()

        }
    }

    if(pullState.isRefreshing){
        LaunchedEffect(Unit) {
            val apiResponse = withContext(Dispatchers.IO){
                if(request != null){
                    RetrofitConfig.api
                        .getNews(
                            q = request.q,
                            from = request.from,
                            to = request.to,
                            language = if(request.language == "") "es" else request.language,
                            apiKey = getString(context, R.string.apiKey)
                        )
                }else{
                    RetrofitConfig.api
                        .getNews(
                            q = "news",
                            language = "es",
                            apiKey = getString(context, R.string.apiKey)
                        )
                }
            }

            if(apiResponse.isSuccessful){
                apiResponse.body()?.let {
                    newResponse = it
                }

                articulos = newResponse?.articles ?: emptyList()
            }


            pullState.endRefresh()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = colorResource(id = R.color.background),
        topBar = {
            TopAppBar(
                modifier = Modifier.height(50.dp),
                title = { /*TODO*/ },
                colors = TopAppBarDefaults
                    .topAppBarColors(containerColor = colorResource(id = R.color.background))
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = colorResource(id = R.color.bottomBarBackground),
                contentColor = colorResource(id = R.color.black),
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                        IconButton(
                            onClick = {
                                (context as? Activity)?.finishAffinity()
                            }
                        ) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Search"
                            )
                        }
                        IconButton(
                            onClick = {
                                navController.navigate(ROOT_FILTER_PAGE)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.filter),
                                contentDescription = "Search"
                            )
                        }
                    }
                }
            )
        }
    ) {
        innerPadding ->

        var scaffoldPadding = innerPadding
        Box(
             modifier = Modifier
                 .fillMaxSize()
                 .padding(start = 10.dp, end = 10.dp, top = innerPadding.calculateTopPadding())
                 .background(color = colorResource(id = R.color.background))
                 .nestedScroll(pullState.nestedScrollConnection)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "Parcial News",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                )

                if(articulos.isNotEmpty()){
                    articulos = articulos.filter {
                        it.title != "[Removed]"
                    }.onEach { item ->
                        if(item.author == null){
                            item.author = "No author"
                        }
                        if(item.title == null){
                            item.title = "Title not found"
                        }
                        if(item.description == null){
                            item.description = "Description not found"
                        }
                        if (item.url == null){
                            item.url = "url not found"
                        }
                        if(item.content == null){
                            item.content = "Content not found"
                        }
                        if(item.publishedAt == null){
                            item.publishedAt = "Date not found"
                        }
                        if(item.urlToImage == null){
                            item.urlToImage = stringResource(id = R.string.errorImageNoFound)
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, top = 15.dp, end = 5.dp)
                    ) {
                        items(articulos){
                                item ->
                            ElevatedCard(
                                onClick = { /*TODO*/ },
                                elevation = CardDefaults
                                    .elevatedCardElevation(
                                        defaultElevation = 6.dp
                                    ),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = CardDefaults
                                    .cardColors(containerColor = colorResource(id = R.color.white)),
                                shape = ShapeDefaults.Small
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                ){
                                    AsyncImage(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .clip(RoundedCornerShape(10.dp)),
                                        model = item.urlToImage,
                                        contentDescription = "image",
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = item.title,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 2
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = item.description,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 2
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(15.dp))

                        }
                    }
                }
                else{
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = innerPadding.calculateBottomPadding()),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "No hay noticias que mostrar",
                            textAlign = TextAlign.Center
                        )
                    }
                }



            }
            PullToRefreshContainer(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                state = pullState
            )
        }
    }

}

@Preview(
    showBackground = true
)
@Composable
fun MainPagePreview(){

    ParcialApiTheme(
        dynamicColor = false
    ) {

        var response = ArticuloResponse(
            listOf(
                Articulo(
                    title = "articulo xd",
                    description = "esto es una prueba :p",
                    url = "ninguna",
                    author = "nadie",
                    content = "hola",
                    urlToImage = stringResource(id = R.string.errorImageNoFound),
                    publishedAt = "hoy"
                ),

            )
        )

        MainPage(response, null, rememberNavController())
    }
}