package com.example.agy

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agy.data.Note
import com.example.agy.data.NoteRepository
import com.example.agy.data.ThemeMode
import com.example.agy.data.ThemePreference
import com.example.agy.ui.CurrentScreen
import com.example.agy.ui.NotesScreen
import com.example.agy.ui.NotesUiState
import com.example.agy.ui.NotesViewModel
import com.example.agy.ui.NotesViewModelFactory
import com.example.agy.ui.SettingsScreen
import com.example.agy.ui.theme.AgyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val viewModel: NotesViewModel = viewModel(
                factory = NotesViewModelFactory(
                    repository = NoteRepository(context.filesDir),
                    themePreference = ThemePreference(context)
                )
            )
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            NotesApp(
                uiState = uiState,
                onAddNote = viewModel::addNote,
                onDeleteNote = viewModel::deleteNote,
                onNavigateToSettings = { viewModel.navigateTo(CurrentScreen.SETTINGS) },
                onThemeSelected = viewModel::setThemeMode,
                onNavigateBack = { viewModel.navigateTo(CurrentScreen.NOTES) }
            )
        }
    }
}

/**
 * Uygulamanın ana container bileşeni. Tema modunu dinamik olarak uygular
 * ve ekran durumuna göre görünümü değiştirir.
 */
@Composable
fun NotesApp(
    uiState: NotesUiState,
    onAddNote: (String) -> Unit,
    onDeleteNote: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    onThemeSelected: (ThemeMode) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = when (uiState.themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    AgyTheme(darkTheme = isDark) {
        Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
            when (uiState.currentScreen) {
                CurrentScreen.NOTES -> NotesScreen(
                    notes = uiState.notes,
                    onAddNote = onAddNote,
                    onDeleteNote = onDeleteNote,
                    onNavigateToSettings = onNavigateToSettings,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
                CurrentScreen.SETTINGS -> SettingsScreen(
                    currentTheme = uiState.themeMode,
                    onThemeSelected = onThemeSelected,
                    onNavigateBack = onNavigateBack,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
        }
    }
}

@Preview(name = "NotesApp Light", showBackground = true)
@Composable
private fun NotesAppPreviewLight() {
    NotesApp(
        uiState = NotesUiState(
            notes = listOf(
                Note(id = "1", text = "Proje planlaması yapıldı"),
                Note(id = "2", text = "Aşama D geliştirmesi tamamlandı")
            ),
            themeMode = ThemeMode.LIGHT,
            currentScreen = CurrentScreen.NOTES
        ),
        onAddNote = {},
        onDeleteNote = {},
        onNavigateToSettings = {},
        onThemeSelected = {},
        onNavigateBack = {}
    )
}

@Preview(name = "NotesApp Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun NotesAppPreviewDark() {
    NotesApp(
        uiState = NotesUiState(
            notes = listOf(
                Note(id = "1", text = "Proje planlaması yapıldı"),
                Note(id = "2", text = "Aşama D geliştirmesi tamamlandı")
            ),
            themeMode = ThemeMode.DARK,
            currentScreen = CurrentScreen.NOTES
        ),
        onAddNote = {},
        onDeleteNote = {},
        onNavigateToSettings = {},
        onThemeSelected = {},
        onNavigateBack = {}
    )
}
