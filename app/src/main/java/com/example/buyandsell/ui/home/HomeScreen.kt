package com.example.buyandsell.ui.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.buyandsell.data.model.Category
import com.example.buyandsell.viewmodel.HomeViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = getViewModel()
) {
    val products by viewModel.products.collectAsState()
    var currentProductIndex by remember { mutableStateOf(0) }
    viewModel.loadProducts()
    val context = LocalContext.current

    val categories = listOf(
        Category("Cars"),
        Category("Bikes"),
        Category("Mobiles"),
        Category("Electronics"),
        Category("Furniture"),
        Category("Properties"),
        Category("Jobs"),
        Category("Services"),
        Category("Fashion"),
        Category("Others")
    )

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "Buy and Sell") },
                    actions = {
                        IconButton(onClick = { navController.navigate("inbox") }) {
                            Icon(Icons.Filled.Inbox, contentDescription = "Inbox")
                        }
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Location", fontWeight = FontWeight.Bold)
                    IconButton(onClick = { /* TODO: Location picker */ }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Location Picker")
                    }
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text("Search") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
                items(categories) { category ->
                    Button(
                        onClick = { /* TODO: Filter by category */ },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Text(text = category.name)
                    }
                }
            }

            if (products.isNotEmpty() && currentProductIndex < products.size) {
                val product = products[currentProductIndex]
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SwipeableCard(
                        product = product,
                        onSwipeLeft = {
                            currentProductIndex++
                        },
                        onSwipeRight = {
                            currentProductIndex++
                        },
                        onSwipeUp = {
                            // TODO: Navigate to product details
                        }
                    )
                }
            } else if (products.isNotEmpty() && currentProductIndex >= products.size) {
                Text(text = "No more products")
            } else {
                CircularProgressIndicator()
            }

            // Action buttons
            if (products.isNotEmpty() && currentProductIndex < products.size) {
                val product = products[currentProductIndex]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        viewModel.createChat(product.id, product.sellerId) { chatId ->
                            navController.navigate("chat/$chatId")
                        }
                    }) {
                        Text(text = "❌")
                    }
                    Button(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://wa.me/${product.phoneNumber}")
                        }
                        context.startActivity(intent)
                    }) {
                        Text(text = "🟢")
                    }
                    Button(onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${product.phoneNumber}")
                        }
                        context.startActivity(intent)
                    })
                    {
                        Text(text = "📞")
                    }
                }
            }
        }
    }
}