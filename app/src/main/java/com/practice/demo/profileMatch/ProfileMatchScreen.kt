package com.practice.demo.profileMatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.practice.demo.domain.UserResult
import com.practice.demo.ui.theme.Teal
import com.practice.demo.uiComponents.AnimatedMessage
import com.practice.demo.uiComponents.HeadingText
import com.practice.demo.uiComponents.ProfilePicture
import com.practice.demo.uiComponents.SelectionButton
import com.practice.demo.uiComponents.SubHeadingText
import com.practice.demo.utils.CommonDescriptionString
import com.practice.demo.utils.CommonString


@Composable
fun MatchCard(
    viewmodel: MatchProfileViewModel
    ) {

    LaunchedEffect(Unit) {
        viewmodel.getProfileList()
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding()
    ) {
        if(viewmodel.state.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Teal
            )
        }
        else {
            Column(modifier = Modifier.fillMaxSize()) {
                HeadingText(
                    inputText = CommonString.MATCH_PROFILE,
                    modifier = Modifier.padding(16.dp)
                )
                if (viewmodel.state.error) {
                    AnimatedMessage(
                        message = viewmodel.state.errorMessage,
                        visible = viewmodel.state.error,
                        backgroundColor = MaterialTheme.colorScheme.errorContainer,
                        textColor = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier
                            .padding(16.dp)
                            .systemBarsPadding()
                            .fillMaxWidth(),
                        onDismiss = {
                            // viewModel.clearError()
                        }
                    )
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = viewmodel.state.listOfProfile,
                        key = { profile ->
                            profile.login?.uuid ?: ""
                        }
                    ) { profile ->
                        MatchCard(
                            viewmodel = viewmodel,
                            match = profile
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MatchCard(
    viewmodel: MatchProfileViewModel,
    match: UserResult
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Picture
            var accepted by remember { mutableStateOf(false) }
            var choosed by remember { mutableStateOf(false) }

            ProfilePicture(
                imageUrl = match.picture?.large,
                contentDescription = CommonDescriptionString.PROFILE_PICTURE
            )

            Spacer(modifier = Modifier.height(16.dp))

            HeadingText(
                inputText = "${match.name?.title} ${match.name?.first} ${match.name?.last}, ${match.dob?.age}",
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = CommonDescriptionString.LOCATION,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))

                SubHeadingText(
                    inputText = "${match.location?.city}, ${match.location?.country}",
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = CommonDescriptionString.CALL,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
                SubHeadingText(
                    inputText = viewmodel.smartTruncate(match.phone,8, 10)
                )
                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = CommonDescriptionString.EMAIL,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
                SubHeadingText(
                    inputText = viewmodel.smartTruncate(match.email,8, 10)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if(!choosed) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    SelectionButton(
                        isSelected = false,
                        icon = Icons.Default.Close,
                        selectedTintColor = Color.White,
                        nonSelectedTintColor = Color.White,
                        containerColor = Color.Red,
                        onClick = {
                            choosed = true
                            accepted = false
                        },
                    )

                    SelectionButton(
                        isSelected = false,
                        icon = Icons.Default.Check,
                        selectedTintColor = Color.White,
                        nonSelectedTintColor = Color.White,
                        containerColor = Teal,
                        onClick = {
                            choosed = true
                            accepted = true
                        },
                        )
                }
            }
            if(choosed){
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /* Handle accept action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = if(accepted) Teal else Color.Red),
                    shape = CircleShape,
                ) {
                    Text(
                        text = if(accepted) CommonString.ACCEPTED else CommonString.DECLINED,
                        style = TextStyle(
                            color = Color.White
                        )
                    )
                }
            }

        }
    }
}
