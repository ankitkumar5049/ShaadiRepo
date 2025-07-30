package com.practice.demo.uiComponents

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practice.demo.ui.theme.White

@Composable
fun AnimatedMatchCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val animOffsetX = remember { Animatable(400f) }
    LaunchedEffect(Unit) {
        animOffsetX.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )
    }

    Card(
        modifier = modifier
            .graphicsLayer {
                translationX = animOffsetX.value
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        content()
    }
}


@Composable
fun FallingActionButton(
    text: String,
    color: androidx.compose.ui.graphics.Color
) {
    val offsetY = remember { Animatable(-200f) }

    LaunchedEffect(Unit) {
        offsetY.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    (1 - Math.exp((-6 * it).toDouble()) * Math.cos((8 * it).toDouble())).toFloat()
                }
            )
        )
    }

Button(
        modifier = Modifier
            .fillMaxWidth()
            .offset { IntOffset(0, offsetY.value.toInt()) },
        onClick = { /* optional: handle click */ },
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        shape = CircleShape
    ) {
        Text(
            text = text,
            style = TextStyle(color = White, fontSize = 16.sp)
        )
    }
}
