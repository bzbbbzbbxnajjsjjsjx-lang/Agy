package com.example.agy.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.agy.R
import com.example.agy.data.ChatMessage
import com.example.agy.data.MessageRole
import com.example.agy.ui.theme.AgyTheme
import com.example.agy.ui.theme.Dimens

/**
 * Sohbet ana ekranı.
 *
 * @param messages Görüntülenecek sohbet mesajları.
 * @param isLoading Asistan yanıtının beklenip beklenmediği.
 * @param onSendMessage Kullanıcı yeni bir mesaj gönderdiğinde tetiklenen geri çağrı.
 */
@Composable
fun ChatScreen(
    messages: List<ChatMessage>,
    isLoading: Boolean,
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var inputMessage by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.spacingMedium)
    ) {
        if (messages.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.chat_empty_state),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
            ) {
                items(messages, key = { it.id }) { message ->
                    ChatMessageItem(message = message)
                }
                if (isLoading) {
                    item {
                        Text(
                            text = stringResource(R.string.chat_thinking),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(Dimens.spacingSmall)
                        )
                    }
                }
            }
        }

        // Giriş Kutusu ve Gönder Butonu Satırı
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.spacingSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
        ) {
            OutlinedTextField(
                value = inputMessage,
                onValueChange = { inputMessage = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text(stringResource(R.string.chat_input_hint)) },
                singleLine = false,
                maxLines = 3,
                shape = RoundedCornerShape(Dimens.cardCornerRadius)
            )

            Button(
                onClick = {
                    val trimmed = inputMessage.trim()
                    if (trimmed.isNotBlank() && !isLoading) {
                        onSendMessage(trimmed)
                        inputMessage = ""
                    }
                },
                enabled = inputMessage.isNotBlank() && !isLoading,
                shape = RoundedCornerShape(Dimens.cardCornerRadius)
            ) {
                Text(stringResource(R.string.send_message))
            }
        }
    }
}

/**
 * Tekil sohbet mesaj balonu bileşeni.
 */
@Composable
fun ChatMessageItem(
    message: ChatMessage,
    modifier: Modifier = Modifier
) {
    val isUser = message.role == MessageRole.USER
    val alignment = if (isUser) Alignment.End else Alignment.Start
    val containerColor = if (isUser) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = if (isUser) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    val roleLabel = if (isUser) {
        stringResource(R.string.role_user)
    } else {
        stringResource(R.string.role_assistant)
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Text(
            text = roleLabel,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(horizontal = Dimens.spacingExtraSmall)
        )

        Card(
            shape = RoundedCornerShape(Dimens.messageBubbleCornerRadius),
            colors = CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = Dimens.cardElevation),
            modifier = Modifier.padding(top = Dimens.spacingExtraSmall)
        ) {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    horizontal = Dimens.bubblePaddingHorizontal,
                    vertical = Dimens.bubblePaddingVertical
                )
            )
        }
    }
}

@Preview(name = "ChatMessageItem User Light", showBackground = true)
@Composable
private fun ChatMessageItemUserPreviewLight() {
    AgyTheme(darkTheme = false) {
        Surface {
            ChatMessageItem(
                message = ChatMessage(
                    id = "1",
                    text = "Android Compose mimarisinde tek yönlü veri akışı nasıl kurulur?",
                    role = MessageRole.USER
                ),
                modifier = Modifier.padding(Dimens.spacingMedium)
            )
        }
    }
}

@Preview(name = "ChatMessageItem Assistant Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun ChatMessageItemAssistantPreviewDark() {
    AgyTheme(darkTheme = true) {
        Surface {
            ChatMessageItem(
                message = ChatMessage(
                    id = "2",
                    text = "ViewModel durumları StateFlow ile UI'a aktarılır, olaylar ise lambda geri çağrılarıyla ViewModel'e iletilir.",
                    role = MessageRole.ASSISTANT
                ),
                modifier = Modifier.padding(Dimens.spacingMedium)
            )
        }
    }
}

@Preview(name = "ChatScreen Empty Light", showBackground = true)
@Composable
private fun ChatScreenEmptyPreviewLight() {
    AgyTheme(darkTheme = false) {
        Surface {
            ChatScreen(
                messages = emptyList(),
                isLoading = false,
                onSendMessage = {}
            )
        }
    }
}

@Preview(name = "ChatScreen Active Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun ChatScreenActivePreviewDark() {
    AgyTheme(darkTheme = true) {
        Surface {
            ChatScreen(
                messages = listOf(
                    ChatMessage(id = "1", text = "Bugünkü toplantı notları nelerdir?", role = MessageRole.USER),
                    ChatMessage(id = "2", text = "Toplantı notlarınızda mimari sadeleştirme ve UI kuralları yer alıyor.", role = MessageRole.ASSISTANT)
                ),
                isLoading = false,
                onSendMessage = {}
            )
        }
    }
}
