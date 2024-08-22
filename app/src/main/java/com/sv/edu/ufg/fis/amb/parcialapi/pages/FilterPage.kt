package com.sv.edu.ufg.fis.amb.parcialapi.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sv.edu.ufg.fis.amb.parcialapi.R
import com.sv.edu.ufg.fis.amb.parcialapi.ui.theme.ParcialApiTheme

@Composable
fun FilterPage(){

    var q = ""

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
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Close"
                        )
                    }
                    IconButton(
                        onClick = { /*TODO*/ }
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
        var scaffoldPadding = innerPadding

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = scaffoldPadding.calculateTopPadding(), start = 20.dp, end = 20.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
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
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = q,
                        onValueChange = { q = it },
                        label = {
                            Text(text = "Buscar")
                        }
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = q,
                        onValueChange = { q = it },
                        label = {
                            Text(text = "Buscar")
                        }
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = q,
                        onValueChange = { q = it },
                        label = {
                            Text(text = "Buscar")
                        }
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = q,
                        onValueChange = { q = it },
                        label = {
                            Text(text = "Buscar")
                        }
                    )
                }
            }
        }

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
        FilterPage()
    }

}