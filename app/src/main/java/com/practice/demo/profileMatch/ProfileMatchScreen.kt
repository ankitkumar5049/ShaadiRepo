package com.practice.demo.profileMatch

import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.practice.demo.domain.UserResult
import com.practice.demo.ui.theme.Teal
import com.practice.demo.uiComponents.AnimatedMatchCard
import com.practice.demo.uiComponents.AnimatedMessage
import com.practice.demo.uiComponents.FallingActionButton
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
                        key = { it.profile.login?.uuid ?: "" }
                    ) { uiState ->
                        AnimatedMatchCard(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            MatchCard(
                                viewmodel = viewmodel,
                                match = uiState.profile,
                                interaction = uiState.interactionStatus
                            )
                        }
                    }

                    item {
                        if (!viewmodel.state.isLoading) {
                            LaunchedEffect(viewmodel.state.listOfProfile.size) {
                                viewmodel.getProfileList(loadMore = true)
                            }
                        }
                    }

                    if (viewmodel.state.isLoading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MatchCard(
    viewmodel: MatchProfileViewModel,
    match: UserResult,
    interaction: MatchProfileContract.InteractionStatus
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

            ProfilePicture(
                imageUrl = match.picture?.large,
                contentDescription = CommonDescriptionString.PROFILE_PICTURE
            )

            Spacer(modifier = Modifier.height(16.dp))

            HeadingText(
                inputText = "${match.name?.first}, ${match.dob?.age}",
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

            Log.d("TAG", "MatchCard: $interaction")
            if(interaction == MatchProfileContract.InteractionStatus.NONE) {
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
                            viewmodel.updateInteraction(match.login?.uuid ?: "", MatchProfileContract.InteractionStatus.DECLINED)
                        },
                    )

                    SelectionButton(
                        isSelected = false,
                        icon = Icons.Default.Check,
                        selectedTintColor = Color.White,
                        nonSelectedTintColor = Color.White,
                        containerColor = Teal,
                        onClick = {
                            viewmodel.updateInteraction(match.login?.uuid ?: "", MatchProfileContract.InteractionStatus.ACCEPTED)
                        },
                        )
                }
            }
            else{
                FallingActionButton(
                    text = if (interaction == MatchProfileContract.InteractionStatus.ACCEPTED)
                        CommonString.ACCEPTED else CommonString.DECLINED,
                    color = if (interaction == MatchProfileContract.InteractionStatus.ACCEPTED)
                        Teal else Color.Red
                )
            }

        }
    }
}
