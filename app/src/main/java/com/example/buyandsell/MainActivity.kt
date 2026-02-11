package com.example.buyandsell

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.buyandsell.navigation.NavGraph
import com.example.buyandsell.ui.theme.BuyAndSellTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuyAndSellTheme {
                NavGraph()
            }
        }
    }
}