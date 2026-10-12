package com.example.agy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.agy.data.ChatMessage
import com.example.agy.data.MessageRole
import com.example.agy.data.ModelClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * Sohbet arayüzünün durum modeli (SSOT).
 *
 * @param messages Sohbet mesajları listesi.
 * @param inputText Kullanıcının giriş kutusuna yazdığı anlık metin.
 * @param isLoading Asistan yanıtı beklenirken aktif olan yükleniyor durumu.
 */
data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val isLoading: Boolean = false
)

/**
 * Sohbet ekranının durumunu yöneten ve ModelClient ile asistan yanıtlarını üreten ViewModel.
 * Composable fonksiyonlar içinde doğrudan model veya ağ çağrısı yapılmaz.
 */
class ChatViewModel(
    private val modelClient: ModelClient,
    coroutineScope: CoroutineScope? = null
) : ViewModel() {

    private val scope: CoroutineScope = coroutineScope ?: viewModelScope

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun setInputText(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun sendMessage(textToSend: String? = null) {
        val content = (textToSend ?: _uiState.value.inputText).trim()
        if (content.isBlank() || _uiState.value.isLoading) return

        val userMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            text = content,
            role = MessageRole.USER
        )

        _uiState.update { current ->
            current.copy(
                messages = current.messages + userMessage,
                inputText = "",
                isLoading = true
            )
        }

        scope.launch {
            try {
                val responseText = modelClient.generateResponse(content)
                val assistantMessage = ChatMessage(
                    id = UUID.randomUUID().toString(),
                    text = responseText,
                    role = MessageRole.ASSISTANT
                )
                _uiState.update { current ->
                    current.copy(
                        messages = current.messages + assistantMessage,
                        isLoading = false
                    )
                }
            } catch (_: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}

/**
 * ChatViewModel örneğini ModelClient bağımlılığı ile oluşturan fabrika sınıfı.
 */
class ChatViewModelFactory(
    private val modelClient: ModelClient
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(modelClient) as T
        }
        throw IllegalArgumentException("Bilinmeyen ViewModel sinifi: ${modelClass.name}")
    }
}
