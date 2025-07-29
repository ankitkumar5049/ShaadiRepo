package com.practice.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.practice.demo.profileMatch.MatchCard
import com.practice.demo.profileMatch.MatchProfileContract
import com.practice.demo.profileMatch.MatchProfileViewModel
import com.practice.demo.ui.theme.DemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val match = listOf(
                MatchProfileContract(
                    id = "profile_1", // Unique ID
                    gender = "female",
                    title = "Mrs",
                    firstName = "Ella",
                    lastName = "Moore",
                    city = "Blenheim",
                    country = "New Zealand",
                    email = "ella.moore@example.com",
                    age = 44,
                    phone = "(384)-143-7218",
                    largePictureUrl = "https://randomuser.me/api/portraits/women/25.jpg"
                ),
                MatchProfileContract(
                    id = "profile_2", // Unique ID
                    gender = "male",
                    title = "Mr",
                    firstName = "John",
                    lastName = "Doe",
                    city = "London",
                    country = "United Kingdom",
                    email = "john.doe@example.com",
                    age = 32,
                    phone = "(123)-456-7890",
                    largePictureUrl = "https://randomuser.me/api/portraits/men/30.jpg"
                ),
                MatchProfileContract(
                    id = "profile_3", // Unique ID
                    gender = "female",
                    title = "Ms",
                    firstName = "Priya",
                    lastName = "Sharma",
                    city = "Mumbai",
                    country = "India",
                    email = "priya.sharma@example.com",
                    age = 29,
                    phone = "(987)-654-3210",
                    largePictureUrl = "https://randomuser.me/api/portraits/women/60.jpg"
                ),
                MatchProfileContract(
                    id = "profile_4", // Unique ID
                    gender = "male",
                    title = "Mr",
                    firstName = "Kenji",
                    lastName = "Tanaka",
                    city = "Tokyo",
                    country = "Japan",
                    email = "kenji.tanaka@example.com",
                    age = 35,
                    phone = "(012)-345-6789",
                    largePictureUrl = "https://randomuser.me/api/portraits/men/80.jpg"
                )
                // Add more sample profiles or load from your API
            )
            DemoTheme {
                val viewmodel: MatchProfileViewModel = hiltViewModel()
                MatchCard(
                    viewmodel = viewmodel,
                    match
                )
            }
        }
    }
}
