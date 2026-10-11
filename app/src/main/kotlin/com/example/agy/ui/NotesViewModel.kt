package com.example.agy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.agy.data.Note
import com.example.agy.data.NoteRepository
import com.example.agy.data.ThemeMode
import com.example.agy.data.ThemePreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

/**
 * Aktif görüntülenen ekran durumu.
 */
enum class CurrentScreen {
    NOTES,
    SETTINGS
}

/**
 * Notlar ve ayarlar arayüzünün birleşik UI durum modeli (SSOT).
 */
data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val currentScreen: CurrentScreen = CurrentScreen.NOTES,
    val isLoading: Boolean = true
)

/**
 * UI durumlarını yöneten ve dosya/tercih I/O işlemlerini Dispatchers.IO
 * bağlamında yürüten ViewModel. Composable fonksiyonlarda I/O yapılmaz.
 */
class NotesViewModel(
    private val repository: NoteRepository,
    private val themePreference: ThemePreference
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val initialTheme = withContext(Dispatchers.IO) {
                themePreference.getThemeMode()
            }
            val initialNotes = withContext(Dispatchers.IO) {
                repository.loadNotes()
            }
            _uiState.update {
                it.copy(
                    notes = initialNotes,
                    themeMode = initialTheme,
                    isLoading = false
                )
            }
        }
    }

    fun addNote(text: String) {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) return

        val newNote = Note(
            id = UUID.randomUUID().toString(),
            text = trimmed,
            createdAt = System.currentTimeMillis()
        )

        viewModelScope.launch {
            val updatedNotes = _uiState.value.notes + newNote
            _uiState.update { it.copy(notes = updatedNotes) }
            withContext(Dispatchers.IO) {
                repository.saveNotes(updatedNotes)
            }
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            val updatedNotes = _uiState.value.notes.filterNot { it.id == noteId }
            _uiState.update { it.copy(notes = updatedNotes) }
            withContext(Dispatchers.IO) {
                repository.saveNotes(updatedNotes)
            }
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            _uiState.update { it.copy(themeMode = mode) }
            withContext(Dispatchers.IO) {
                themePreference.setThemeMode(mode)
            }
        }
    }

    fun navigateTo(screen: CurrentScreen) {
        _uiState.update { it.copy(currentScreen = screen) }
    }
}

/**
 * Bağımlılıkları enjekte eden ViewModel Factory.
 */
class NotesViewModelFactory(
    private val repository: NoteRepository,
    private val themePreference: ThemePreference
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(repository, themePreference) as T
        }
        throw IllegalArgumentException("Bilinmeyen ViewModel sinifi: ${modelClass.name}")
    }
}
