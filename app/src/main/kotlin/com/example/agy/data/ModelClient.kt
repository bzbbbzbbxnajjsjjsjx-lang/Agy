package com.example.agy.data

import kotlinx.coroutines.delay

/**
 * Dil modeli / yapay zeka asistanı istemci arayüzü.
 */
interface ModelClient {
    /**
     * Kullanıcının girdiği girdiye karşılık asistan yanıtı üretir.
     */
    suspend fun generateResponse(prompt: String): String
}

/**
 * Sabit yanıt ve ayarlanabilir gecikmeyle çalışan sahte model istemcisi.
 *
 * @param responseDelayMs Yanıt üretilmeden önceki bekleme süresi (milisaniye).
 * @param defaultResponse Döndürülecek varsayılan yanıt metni.
 */
class FakeModelClient(
    private val responseDelayMs: Long = 400L,
    private val defaultResponse: String = "Notlarınızı inceledim. Size nasıl yardımcı olabilirim?"
) : ModelClient {
    override suspend fun generateResponse(prompt: String): String {
        if (responseDelayMs > 0) {
            delay(responseDelayMs)
        }
        return defaultResponse
    }
}
