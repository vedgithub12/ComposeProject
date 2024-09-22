package com.example.composeproject.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeproject.model.Article
import com.example.composeproject.service.RetrofitInstance
import com.example.composeproject.ui.theme.ComposeProjectTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeProjectTheme {
                val articles = remember { mutableStateListOf<Article>() }
                val scope = rememberCoroutineScope()

                // Make the API call inside a coroutine
                LaunchedEffect(Unit) {
                    scope.launch(Dispatchers.IO) {
                        try {
                            val response = RetrofitInstance.api.getBlogs().await()
                            articles.addAll(response)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                // Display the fetched data
                ArticleList(articles)
            }
        }
    }

}

@Composable
fun ArticleList(articles: List<Article>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(articles.size) { index ->
            CardImage(article = articles[index])
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewArticleList() {
    ComposeProjectTheme {
        val sampleArticles = listOf(
            Article(
                id = "004780a2-5294-4672-a284-424bebfd8748",
                title = "Consequatur et quisquam minus delectus tenetur.",
                featured_image = "https://images.unsplash.com/photo-1463936575829-25148e1db1b8?crop=entropy&cs=srgb&fm=jpg&ixid=M3w0OTE2MTF8MHwxfHNlYXJjaHwxMHx8UGxhbnRzfGVufDB8MHx8fDE2OTczNTc1NjV8MA&ixlib=rb-4.0.3&q=85"
            ),
            Article(
                id = "00535eb0-76c3-4f12-b1d2-93473cd5fe94",
                title = "Nesciunt eligendi qui delectus quia blanditiis.",
                featured_image = "https://images.unsplash.com/photo-1604213410393-89f141bb96b8?crop=entropy&cs=srgb&fm=jpg&ixid=M3w0OTE2MTF8MHwxfHNlYXJjaHw0fHxKdW5nbGV8ZW58MHwwfHx8MTY5NzM1NzU0MHww&ixlib=rb-4.0.3&q=85"
            )
        )
        ArticleList(sampleArticles)
    }
}
