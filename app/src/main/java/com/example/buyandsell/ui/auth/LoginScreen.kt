package com.example.buyandsell.ui.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.buyandsell.R
import com.example.buyandsell.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.example.buyandsell.ui.theme.SpotifyGreen
import com.example.buyandsell.ui.theme.SpotifyLightGray
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel = getViewModel()) {
    var email by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                coroutineScope.launch {
                    try {
                        val credential = Identity.getSignInClient(context).getSignInCredentialFromIntent(result.data)
                        val idToken = credential.googleIdToken ?: return@launch
                        viewModel.signInWithGoogle(idToken, onSuccess = {
                            navController.navigate("home") { popUpTo(0) }
                        }, onError = {
                            error = it
                        })
                    } catch (e: Exception) {
                        error = "Google Sign-In failed: ${e.localizedMessage}"
                    }
                }
            } else {
                error = "Google Sign-In was cancelled or failed."
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text("Welcome back", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email or username") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = SpotifyLightGray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = SpotifyLightGray,
                cursorColor = SpotifyGreen
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* TODO: Implement multi-step login */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = SpotifyGreen)
        ) {
            Text("Continue", color = Color.Black, modifier = Modifier.padding(vertical = 8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("or", color = SpotifyLightGray)
        Spacer(modifier = Modifier.height(16.dp))

        SocialLoginButton(text = "Continue with phone number", iconResId = R.drawable.ic_phone) { /*TODO*/ }
        Spacer(modifier = Modifier.height(8.dp))
        SocialLoginButton(text = "Continue with Google", iconResId = R.drawable.ic_google) {
            coroutineScope.launch {
                try {
                    val signInRequest = BeginSignInRequest.builder()
                        .setGoogleIdTokenRequestOptions(
                            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setServerClientId(context.getString(R.string.default_web_client_id))
                                .setFilterByAuthorizedAccounts(false)
                                .build()
                        )
                        .build()
                    val result = Identity.getSignInClient(context).beginSignIn(signInRequest).await()
                    googleSignInLauncher.launch(IntentSenderRequest.Builder(result.pendingIntent).build())
                } catch (e: Exception) {
                    error = "Failed to begin Google Sign-In: ${e.localizedMessage}"
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        SocialLoginButton(text = "Continue with Facebook", iconResId = R.drawable.ic_facebook) { /*TODO*/ }
        Spacer(modifier = Modifier.height(8.dp))
        SocialLoginButton(text = "Continue with Apple", iconResId = R.drawable.ic_apple) { /*TODO*/ }

        Spacer(modifier = Modifier.weight(1f))

        error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Don't have an account?", color = SpotifyLightGray)
            TextButton(onClick = { navController.navigate("signup") }) {
                Text("Sign up", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun SocialLoginButton(text: String, iconResId: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, SpotifyLightGray),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent, contentColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(painter = painterResource(id = iconResId), contentDescription = null, modifier = Modifier.size(24.dp))
            Text(text, textAlign = TextAlign.Center, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
        }
    }
}
