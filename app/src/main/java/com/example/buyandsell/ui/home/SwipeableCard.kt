package com.example.buyandsell.ui.home

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.buyandsell.data.model.Product
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment
import kotlin.math.abs

@Composable
fun SwipeableCard(
    product: Product,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    onSwipeUp: () -> Unit
) {
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .graphicsLayer {
                translationX = offsetX.value
                translationY = offsetY.value
                rotationZ = (offsetX.value / 50).coerceIn(-15f, 15f)
            }
            .pointerInput(Unit) {
                coroutineScope {
                    detectDragGestures(
                        onDragEnd = {
                            scope.launch {
                                if (abs(offsetX.value) > 300) {
                                    if (offsetX.value > 0) onSwipeRight() else onSwipeLeft()
                                } else if (offsetY.value < -300) {
                                    onSwipeUp()
                                } else {
                                    launch { offsetX.animateTo(0f, tween(400)) }
                                    launch { offsetY.animateTo(0f, tween(400)) }
                                }
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            scope.launch {
                                launch { offsetX.snapTo(offsetX.value + dragAmount.x) }
                                launch { offsetY.snapTo(offsetY.value + dragAmount.y) }
                            }
                        }
                    )
                }
            },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = product.imageUrls.firstOrNull(),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = product.title, style = MaterialTheme.typography.titleLarge)
                Text(text = product.price, style = MaterialTheme.typography.bodyLarge)
                Text(text = "${product.location} - ${product.postedTime}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Seller: ${product.sellerType}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}