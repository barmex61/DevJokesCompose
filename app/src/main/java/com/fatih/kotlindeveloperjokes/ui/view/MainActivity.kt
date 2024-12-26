package com.fatih.kotlindeveloperjokes.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.fatih.kotlindeveloperjokes.entity.JokeItem
import com.fatih.kotlindeveloperjokes.repository.JokeRepository
import com.fatih.kotlindeveloperjokes.ui.theme.KotlinDeveloperJokesTheme
import com.fatih.kotlindeveloperjokes.ui.viewmodel.JokeViewModel
import com.fatih.kotlindeveloperjokes.util.Constants.SINGLE
import com.fatih.kotlindeveloperjokes.util.Result
import com.fatih.kotlindeveloperjokes.util.Utils
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fatih.kotlindeveloperjokes.util.Utils.jokeRepository


//https://raw.githubusercontent.com/atilsamancioglu/ProgrammingJokesJSON/refs/heads/main/jokes.json

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val jokeViewModel: JokeViewModel = viewModel(factory = JokeViewModel.factory)
            KotlinDeveloperJokesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        JokeScreen(jokeViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun JokeScreen(jokeViewModel: JokeViewModel) {
    val jokesState = jokeViewModel.jokes.observeAsState(initial = Result.Loading())
    when (val result = jokesState.value) {
        is Result.Success -> {
            JokesLazyColumn(result.data!!)
        }
        is Result.Error -> {
            Text(text = result.message!!)
        }
        is Result.Loading -> {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun JokesLazyColumn(items : ArrayList<JokeItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items(items, key = { it }) { jokeItem ->
            AnimatedListItem(jokeItem)
        }
    }
}

@Composable
fun AnimatedListItem(jokeItem: JokeItem) {
    var isExpanded by remember { mutableStateOf(jokeItem.type == SINGLE) }
    val jokeText = remember { mutableStateOf("") }
    LaunchedEffect(isExpanded) {
        jokeText.value = if (jokeItem.type == SINGLE) jokeItem.joke else {
            if (isExpanded) {
                jokeItem.setup + "\n" +jokeItem.delivery
            } else {
                jokeItem.setup
            }
        }
    }

    val animatedVisibility by animateFloatAsState(
        targetValue = if (isExpanded) 0f else 1f,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                isExpanded = !isExpanded
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(8.dp)
                .animateContentSize()
        ) {
            Text(text = jokeText.value)
            if (jokeItem.type != SINGLE && !isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "....Click to see the Punch Line ...",
                    modifier = Modifier.alpha(animatedVisibility).fillMaxWidth(1f),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinDeveloperJokesTheme {

    }
}