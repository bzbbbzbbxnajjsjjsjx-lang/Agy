package com.example.agy

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agy.data.ChatMessage
import com.example.agy.data.FakeModelClient
import com.example.agy.data.MessageRole
import com.example.agy.data.Note
import com.example.agy.data.NoteRepository
import com.example.agy.data.ThemeMode
import com.example.agy.data.ThemePreference
import com.example.agy.ui.ChatScreen
import com.example.agy.ui.ChatUiState
import com.example.agy.ui.ChatViewModel
import com.example.agy.ui.ChatViewModelFactory
import com.example.agy.ui.CurrentScreen
import com.example.agy.ui.NotesScreen
import com.example.agy.ui.NotesUiState
import com.example.agy.ui.NotesViewModel
import com.example.agy.ui.NotesViewModelFactory
import com.example.agy.ui.SettingsScreen
import com.example.agy.ui.theme.AgyTheme
import com.example.agy.ui.theme.Dimens

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val notesViewModel: NotesViewModel = viewModel(
                factory = NotesViewModelFactory(
                    repository = NoteRepository(context.filesDir),
                    themePreference = ThemePreference(context)
                )
            )
            val chatViewModel: ChatViewModel = viewModel(
                factory = ChatViewModelFactory(
                    modelClient = FakeModelClient()
                )
            )

            val notesUiState by notesViewModel.uiState.collectAsStateWithLifecycle()
            val chatUiState by chatViewModel.uiState.collectAsStateWithLifecycle()

            AssistantApp(
                notesUiState = notesUiState,
                chatUiState = chatUiState,
                onSendMessage = chatViewModel::sendMessage,
                onAddNote = notesViewModel::addNote,
                onUpdateNote = notesViewModel::updateNote,
                onStartEditing = notesViewModel::startEditing,
                onCancelEditing = notesViewModel::cancelEditing,
                onSearchQueryChange = notesViewModel::setSearchQuery,
                onDeleteNote = notesViewModel::deleteNote,
                onNavigateTo = notesViewModel::navigateTo,
                onThemeSelected = notesViewModel::setThemeMode
            )
        }
    }
}

/**
 * Uygulamanın ana container bileşeni.
 * Sohbet ve Notlar arasında TabRow ile gezinme sağlar, Ayarlar ekranını yönetir.
 */
@Composable
fun AssistantApp(
    notesUiState: NotesUiState,
    chatUiState: ChatUiState,
    onSendMessage: (String) -> Unit,
    onAddNote: (String) -> Unit,
    onUpdateNote: (String, String) -> Unit,
    onStartEditing: (Note) -> Unit,
    onCancelEditing: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onDeleteNote: (String) -> Unit,
    onNavigateTo: (CurrentScreen) -> Unit,
    onThemeSelected: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = when (notesUiState.themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    AgyTheme(darkTheme = isDark) {
        Scaffold(
            topBar = {
                if (notesUiState.currentScreen != CurrentScreen.SETTINGS) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Dimens.spacingMedium, vertical = Dimens.spacingSmall),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(
                                    if (notesUiState.currentScreen == CurrentScreen.CHAT) R.string.chat_title else R.string.notes_title
                                ),
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            TextButton(onClick = { onNavigateTo(CurrentScreen.SETTINGS) }) {
                                Text(
                                    text = stringResource(R.string.settings_title),
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }

                        TabRow(
                            selectedTabIndex = if (notesUiState.currentScreen == CurrentScreen.CHAT) 0 else 1,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Tab(
                                selected = notesUiState.currentScreen == CurrentScreen.CHAT,
                                onClick = { onNavigateTo(CurrentScreen.CHAT) },
                                text = { Text(stringResource(R.string.tab_chat)) }
                            )
                            Tab(
                                selected = notesUiState.currentScreen == CurrentScreen.NOTES,
                                onClick = { onNavigateTo(CurrentScreen.NOTES) },
                                text = { Text(stringResource(R.string.tab_notes)) }
                            )
                        }
                    }
                }
            },
            modifier = modifier.fillMaxSize()
        ) { innerPadding ->
            when (notesUiState.currentScreen) {
                CurrentScreen.CHAT -> ChatScreen(
                    messages = chatUiState.messages,
                    isLoading = chatUiState.isLoading,
                    onSendMessage = onSendMessage,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
                CurrentScreen.NOTES -> NotesScreen(
                    notes = notesUiState.filteredNotes,
                    searchQuery = notesUiState.searchQuery,
                    onSearchQueryChange = onSearchQueryChange,
                    editingNote = notesUiState.editingNote,
                    onAddNote = onAddNote,
                    onUpdateNote = onUpdateNote,
                    onStartEditing = onStartEditing,
                    onCancelEditing = onCancelEditing,
                    onDeleteNote = onDeleteNote,
                    onNavigateToSettings = { onNavigateTo(CurrentScreen.SETTINGS) },
                    showHeader = false,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
                CurrentScreen.SETTINGS -> SettingsScreen(
                    currentTheme = notesUiState.themeMode,
                    onThemeSelected = onThemeSelected,
                    onNavigateBack = { onNavigateTo(CurrentScreen.CHAT) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
        }
    }
}

@Preview(name = "AssistantApp Chat Light", showBackground = true)
@Composable
private fun AssistantAppChatPreviewLight() {
    AssistantApp(
        notesUiState = NotesUiState(
            currentScreen = CurrentScreen.CHAT,
            themeMode = ThemeMode.LIGHT
        ),
        chatUiState = ChatUiState(
            messages = listOf(
                ChatMessage(id = "1", text = "Merhaba", role = MessageRole.USER),
                ChatMessage(id = "2", text = "Size nasıl yardımcı olabilirim?", role = MessageRole.ASSISTANT)
            )
        ),
        onSendMessage = {},
        onAddNote = {},
        onUpdateNote = { _, _ -> },
        onStartEditing = {},
        onCancelEditing = {},
        onSearchQueryChange = {},
        onDeleteNote = {},
        onNavigateTo = {},
        onThemeSelected = {}
    )
}

@Preview(name = "AssistantApp Notes Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun AssistantAppNotesPreviewDark() {
    AssistantApp(
        notesUiState = NotesUiState(
            notes = listOf(
                Note(id = "1", text = "Aşama E başarıyla tamamlandı"),
                Note(id = "2", text = "Not asistanı sohbet entegrasyonu")
            ),
            currentScreen = CurrentScreen.NOTES,
            themeMode = ThemeMode.DARK
        ),
        chatUiState = ChatUiState(),
        onSendMessage = {},
        onAddNote = {},
        onUpdateNote = { _, _ -> },
        onStartEditing = {},
        onCancelEditing = {},
        onSearchQueryChange = {},
        onDeleteNote = {},
        onNavigateTo = {},
        onThemeSelected = {}
    )
}
