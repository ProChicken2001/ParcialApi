package com.sv.edu.ufg.fis.amb.parcialapi.pages

import android.icu.util.LocaleData
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.sv.edu.ufg.fis.amb.parcialapi.R
import com.sv.edu.ufg.fis.amb.parcialapi.models.ArticuloRequest
import com.sv.edu.ufg.fis.amb.parcialapi.routes.ROOT_MAIN_PAGE
import com.sv.edu.ufg.fis.amb.parcialapi.ui.theme.ParcialApiTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterPage(
    navController: NavHostController
){

    val context = LocalContext.current

    var showFromDataPicker by remember { mutableStateOf(false) }
    var showToDataPicker by remember { mutableStateOf(false) }
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    var q by remember { mutableStateOf("") }
    var from by remember { mutableStateOf<Long?>(null) }
    var to by remember { mutableStateOf<Long?>(null) }
    var language by remember { mutableStateOf("") }

    var selectFromDate by remember { mutableStateOf("") }
    var selectToDate by remember { mutableStateOf("") }

    if(from != null){
        val newDate = Date(from!!)
        selectFromDate = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).format(newDate)
    }else{
        selectFromDate = formatter.format(Date())
    }

    if(to != null){
        val newDate = Date(to!!)
        selectToDate = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).format(newDate)
    }else{
        selectToDate = formatter.format(Date())
    }

    var articuloRequest : ArticuloRequest? = null

    Scaffold(
        containerColor = colorResource(id = R.color.background),
        bottomBar = {
            BottomAppBar(
                containerColor = colorResource(id = R.color.bottomBarBackground)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = {
                            val jsonParam = Gson().toJson(articuloRequest)
                            navController.navigate("${ROOT_MAIN_PAGE}/${jsonParam}") }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Close"
                        )
                    }
                    IconButton(
                        onClick = {
                            q = ""
                            language = ""
                            selectFromDate = formatter.format(Date())
                            selectToDate = formatter.format(Date())
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Close"
                        )
                    }

                }
            }
        }

    ) {
        innerPadding ->
        val scaffoldPadding = innerPadding

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = scaffoldPadding.calculateTopPadding(),
                    start = 20.dp,
                    end = 20.dp,
                    bottom = scaffoldPadding.calculateBottomPadding()
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Parcial News",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(35.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults
                    .cardColors(containerColor = colorResource(id = R.color.white)),
                elevation = CardDefaults.elevatedCardElevation(5.dp),
                onClick = { /*TODO*/ }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = q,
                        onValueChange = { q = it },
                        label = {
                            Text(text = "Buscar")
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = selectFromDate,
                        onValueChange = {  },
                        label = {
                            Text(
                                text = "Desde"
                            )
                        },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    showFromDataPicker = !showFromDataPicker
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.DateRange,
                                    contentDescription = "Date Picker")
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = selectToDate,
                        onValueChange = {  },
                        label = {
                            Text(
                                text = "Hasta"
                            )
                        },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    showToDataPicker = !showToDataPicker
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.DateRange,
                                    contentDescription = "Date Picker")
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = language,
                        onValueChange = { language = it },
                        label = {
                            Text(text = "Lenguaje")
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                        ElevatedButton(
                            onClick = {

                                val minDate = formatter.parse("2024-07-25")
                                val dateFrom = formatter.parse(selectFromDate)
                                val dateTo = formatter.parse(selectToDate)

                                if (dateFrom != null && dateTo != null) {
                                    if(
                                        dateFrom.before(minDate) ||
                                        dateTo.before(minDate) ||
                                        dateFrom.after(dateTo) ||
                                        dateTo.before(dateFrom)
                                        ){

                                        Toast.makeText(
                                            context,
                                            getString(context, R.string.toastTextFiltrar),
                                            Toast.LENGTH_LONG
                                        ).show()

                                    }else{
                                        articuloRequest = ArticuloRequest(
                                            q = q.ifEmpty { "news" },
                                            from = if(selectFromDate == formatter.format(Date()))
                                                "" else selectFromDate,
                                            to = if(selectToDate == formatter.format(Date()))
                                                "" else selectToDate,
                                            language = language
                                        )
                                        val jsonParam = Gson().toJson(articuloRequest)
                                        navController.navigate("${ROOT_MAIN_PAGE}/${jsonParam}")
                                    }

                                }

                            },
                            elevation = ButtonDefaults.elevatedButtonElevation(3.5.dp),
                            colors = ButtonDefaults.elevatedButtonColors(
                                contentColor = colorResource(id = R.color.black)
                            )
                        ) {
                            Text("Filtrar")
                        }
                        ElevatedButton(
                            onClick = {
                                val jsonParam = Gson().toJson(articuloRequest)
                                navController.navigate("${ROOT_MAIN_PAGE}/${jsonParam}")
                            },
                            elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
                            colors = ButtonDefaults.elevatedButtonColors(
                                contentColor = colorResource(id = R.color.black)
                            )
                        ) {
                            Text("Cancelar")
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        if(showFromDataPicker){
            DatePickerModal(
                onDateSelected = {
                    from = it
                    showFromDataPicker = false
                },
                onDismiss = {
                    showFromDataPicker = false
                }
            )
        }

        if(showToDataPicker){
            DatePickerModal(
                onDateSelected = {
                    to = it
                    showToDataPicker = false
                },
                onDismiss = {
                    showToDataPicker = false
                }
            )
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
){
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = "CANCEL")
            }
        }

    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults
                .colors(containerColor = colorResource(id = R.color.bottomBarBackground))
        )
    }

}

@Preview
@Composable
fun DatePickerPreview(){
    ParcialApiTheme(
        dynamicColor = false
    ){
        DatePickerModal(onDateSelected = {}, onDismiss = {})
    }
}

@Preview(
    showBackground = true
)
@Composable
fun FilterPagePreview(){

    ParcialApiTheme(
        dynamicColor = true
    ) {
        FilterPage(rememberNavController())
    }

}