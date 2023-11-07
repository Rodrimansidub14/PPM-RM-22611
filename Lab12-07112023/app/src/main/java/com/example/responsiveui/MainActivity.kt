package com.example.responsiveui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyResponsiveApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyResponsiveApp() {
    val snackbarHostState = remember { SnackbarHostState() }

    MaterialTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            content = { paddingValues ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    AdaptiveLayout(snackbarHostState = snackbarHostState)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveLayout(snackbarHostState: SnackbarHostState) {
    val configuration = LocalConfiguration.current
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (userInputArea, submitButtonArea, listGridArea) = createRefs()

        UserInput(
            modifier = Modifier
                .constrainAs(userInputArea) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(submitButtonArea.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                },
            snackbarHostState = snackbarHostState
        )

        Button(
            onClick = { /* TODO: Implement button action */ },
            modifier = Modifier
                .constrainAs(submitButtonArea) {
                    end.linkTo(parent.end, margin = 16.dp)
                    top.linkTo(userInputArea.top)
                    bottom.linkTo(userInputArea.bottom)
                }
        ) {
            Text("Submit")
        }

        AdaptiveListGrid(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(listGridArea) {
                    top.linkTo(userInputArea.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            configuration = configuration
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInput(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState) {
    var textState by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(16.dp)) {
        // Image Composable
        Image(
            painter = painterResource(id = R.drawable.img1), // Replace with your image resource
            contentDescription = "Displayed image",
            modifier = Modifier.size(100.dp) // Set the size as needed
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Text Field for user input
        TextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text("Enter text") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Button to show Snackbar
        Button(onClick = { showSnackbar = true }) {
            Text("Submit")
        }

        // LaunchedEffect to show the Snackbar based on showSnackbar state
        LaunchedEffect(showSnackbar) {
            if (showSnackbar) {
                val result = snackbarHostState.showSnackbar(
                    message = "This is a message",
                    actionLabel = "Hide",
                    duration = SnackbarDuration.Short
                )
                // Handle the result if necessary
                showSnackbar = false
            }
        }
    }
}


@Composable
fun AdaptiveListGrid(modifier: Modifier = Modifier, configuration: Configuration) {
    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        GridContent(modifier = modifier)
    } else {
        ListContent(modifier = modifier)
    }
}



@Composable
fun ListContent(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(4) {
            ListItem(item = "Item #$it")
        }
    }
}

@Composable
fun ListItem(item: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.img1), // Replace with your image resource
            contentDescription = "List item image",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = item, modifier = Modifier.weight(1f).align(Alignment.CenterVertically))
    }
}

@Composable
fun GridContent(modifier: Modifier = Modifier) {
    LazyVerticalGrid(columns = GridCells.Fixed(4), modifier = modifier) {
        items(3) {
            Card(modifier = Modifier.padding(8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.img1), // Replace with your image resource
                    contentDescription = "Grid item image",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}



