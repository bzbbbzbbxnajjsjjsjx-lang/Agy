package com.example.agy.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Tasarım sistemi ve arayüz boşluk (spacing/padding/sizing) tokenları.
 * Ham dp sabitleri doğrudan Composable fonksiyonlar içine yazılmaz,
 * bu sınıf üzerinden erişilir.
 */
object Dimens {
    val spacingExtraSmall: Dp = 4.dp
    val spacingSmall: Dp = 8.dp
    val spacingMedium: Dp = 16.dp
    val spacingLarge: Dp = 24.dp
    val spacingExtraLarge: Dp = 32.dp

    val dividerThickness: Dp = 1.dp
    val cardCornerRadius: Dp = 12.dp
    val cardElevation: Dp = 2.dp
    val minTouchTarget: Dp = 48.dp

    val messageBubbleCornerRadius: Dp = 16.dp
    val messageBubbleCornerRadiusSmall: Dp = 4.dp
    val bubblePaddingHorizontal: Dp = 12.dp
    val bubblePaddingVertical: Dp = 8.dp
}
