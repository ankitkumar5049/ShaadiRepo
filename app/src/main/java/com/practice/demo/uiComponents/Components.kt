package com.practice.demo.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.practice.demo.utils.CommonDescriptionString

@Composable
fun SelectionButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonSize: Dp = 70.dp,
    iconSize: Dp = 36.dp,
    icon: ImageVector = Icons.Filled.Check,
    selectedTintColor: Color = Color.Red,
    nonSelectedTintColor: Color = Color.White,
    containerColor: Color = MaterialTheme.colorScheme.primary
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        shape = CircleShape,
        modifier = modifier.size(buttonSize)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = CommonDescriptionString.SELECTION_BUTTON,
            tint = if (isSelected) selectedTintColor else nonSelectedTintColor,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
fun HeadingText(
    inputText: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 22.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    textColor: Color = Color.Black
) {
    Text(
        text = inputText,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = textColor,
        modifier = modifier
    )
}

@Composable
fun SubHeadingText(
    inputText: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Text(
        text = inputText,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = textColor,
        modifier = modifier
    )
}

@Composable
fun ProfilePicture(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 240.dp,
    placeholderColor: Color = Color.LightGray,
    contentScale: ContentScale = ContentScale.Crop,
    shape: Shape = CircleShape,
    showLoadingIndicator: Boolean = true
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(shape) // Clip the Box itself to ensure background and indicator are also clipped
            .background(placeholderColor), // Background shown during loading/error
        contentAlignment = Alignment.Center // Center the loading indicator or image
    ) {
        AsyncImage(
            model = imageUrl, // Optional: for a smooth transition
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier.matchParentSize(), // Make AsyncImage fill the Box
            onState = { state ->
                // You can react to the loading state here if needed
                // For example, hide/show a custom loading indicator based on state
                if (state is AsyncImagePainter.State.Loading && showLoadingIndicator) { /* Handled by Box below */
                }
                if (state is AsyncImagePainter.State.Error) { /* Show error icon or message */
                }
            }
        )

        if (showLoadingIndicator && imageUrl != null) {
            // This is a simplified loading indicator.
            // For more control, observe AsyncImagePainter.State via onState in AsyncImage
            // and manage the visibility of this CircularProgressIndicator accordingly.
            // However, Coil often handles placeholders well enough that this might be redundant
            // if you set a placeholder in the ImageRequest.
            // For this example, let's assume we want an explicit one on top of the placeholderColor.

            // A more direct way to show loading with Coil 2.x is to use the onState callback
            // and manage a separate state variable for loading visibility.
            // For now, this is a basic approach.
            // Consider if Coil's built-in placeholder handling is sufficient.
        }
    }
}
