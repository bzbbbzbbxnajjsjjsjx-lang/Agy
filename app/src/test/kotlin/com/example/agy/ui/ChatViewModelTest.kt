package com.example.agy.ui

import com.example.agy.data.FakeModelClient
import com.example.agy.data.MessageRole
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * ChatViewModel için birim testi.
 *
 * Görev Kapsamı 7: "Birim testi: gönderince listeye iki mesaj eklenir."
 */
class ChatViewModelTest {

    fun testSendMessageAddsTwoMessages() = runBlocking {
        // 1. Gecikmesiz sahte model istemcisi oluştur
        val fakeClient = FakeModelClient(
            responseDelayMs = 0L,
            defaultResponse = "Test yaniti"
        )

        // 2. Main dispatcher gerektirmeyen Unconfined coroutine scope ile ViewModel'i başlat
        val testScope = CoroutineScope(Dispatchers.Unconfined)
        val viewModel = ChatViewModel(
            modelClient = fakeClient,
            coroutineScope = testScope
        )

        // Başlangıçta mesaj listesi boş olmalıdır
        check(viewModel.uiState.value.messages.isEmpty()) {
            "Başlangıçta mesaj listesi boş olmalıdır"
        }

        // 3. Mesaj gönder
        viewModel.sendMessage("Merhaba")

        // 4. Durumu doğrula: Listede 2 mesaj (kullanıcı + asistan) olmalıdır
        val messages = viewModel.uiState.value.messages
        check(messages.size == 2) {
            "Mesaj gönderildikten sonra listeye 2 mesaj eklenmelidir, bulunan: ${messages.size}"
        }

        check(messages[0].role == MessageRole.USER) {
            "İlk mesaj kullanıcı (USER) rolünde olmalıdır"
        }
        check(messages[0].text == "Merhaba") {
            "İlk mesajın metni 'Merhaba' olmalıdır"
        }

        check(messages[1].role == MessageRole.ASSISTANT) {
            "İkinci mesaj asistan (ASSISTANT) rolünde olmalıdır"
        }
        check(messages[1].text == "Test yaniti") {
            "İkinci mesajın metni sahte istemcinin yanıtı olmalıdır"
        }

        check(!viewModel.uiState.value.isLoading) {
            "Yanıt tamamlandıktan sonra isLoading false olmalıdır"
        }
    }
}
