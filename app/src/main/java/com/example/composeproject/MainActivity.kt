package com.example.composeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.composeproject.ui.theme.ComposeProjectTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeProjectTheme {
                val articles = remember { getArticlesFromJson() }
                ArticleList(articles)
            }
        }
    }

    // Parse the JSON data using Gson
    private fun getArticlesFromJson(): List<Article> {
        val jsonData = """
            [
                {
                    "id": "004780a2-5294-4672-a284-424bebfd8748",
                    "title": "Consequatur et quisquam minus delectus tenetur.",
                    "featured_image": "https://images.unsplash.com/photo-1463936575829-25148e1db1b8?crop=entropy&cs=srgb&fm=jpg&ixid=M3w0OTE2MTF8MHwxfHNlYXJjaHwxMHx8UGxhbnRzfGVufDB8MHx8fDE2OTczNTc1NjV8MA&ixlib=rb-4.0.3&q=85"
                },
                {
                    "id": "00535eb0-76c3-4f12-b1d2-93473cd5fe94",
                    "title": "Nesciunt eligendi qui delectus quia blanditiis.",
                    "featured_image": "https://images.unsplash.com/photo-1604213410393-89f141bb96b8?crop=entropy&cs=srgb&fm=jpg&ixid=M3w0OTE2MTF8MHwxfHNlYXJjaHw0fHxKdW5nbGV8ZW58MHwwfHx8MTY5NzM1NzU0MHww&ixlib=rb-4.0.3&q=85"
                }
            ]
        """
        val listType = object : TypeToken<List<Article>>() {}.type
        return Gson().fromJson(jsonData, listType)
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

@Composable
fun ArticleCard(article: Article) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(15.dp),
    ) {
        Box {
            // Load image using Coil library
            Image(
                painter = rememberImagePainter(data = article.featured_image),
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = article.title,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
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
