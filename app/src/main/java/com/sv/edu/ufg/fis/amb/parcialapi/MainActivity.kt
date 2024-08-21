package com.sv.edu.ufg.fis.amb.parcialapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sv.edu.ufg.fis.amb.parcialapi.models.Articulo
import com.sv.edu.ufg.fis.amb.parcialapi.models.ArticuloResponse
import com.sv.edu.ufg.fis.amb.parcialapi.pages.MainPage
import com.sv.edu.ufg.fis.amb.parcialapi.ui.theme.ParcialApiTheme

class MainActivity : ComponentActivity() {

    lateinit var navController : NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            ParcialApiTheme(
                dynamicColor = false
            ) {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}