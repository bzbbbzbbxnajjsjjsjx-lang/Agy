package com.example.agy.data

/**
 * Mesajı gönderen tarafın rolü.
 */
enum class MessageRole {
    USER,
    ASSISTANT
}

/**
 * Sohbet mesajı veri modeli.
 *
 * @param id Benzersiz mesaj kimliği.
 * @param text Mesajın içeriği.
 * @param role Mesajı gönderenin rolü (Kullanıcı / Asistan).
 * @param timestamp Mesajın gönderildiği zaman damgası.
 */
data class ChatMessage(
    val id: String,
    val text: String,
    val role: MessageRole,
    val timestamp: Long = System.currentTimeMillis()
)
