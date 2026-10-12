package com.example.agy.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.agy.R
import com.example.agy.data.Note
import com.example.agy.ui.theme.AgyTheme
import com.example.agy.ui.theme.Dimens
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Notlar ana ekranı.
 */
@Composable
fun NotesScreen(
    notes: List<Note>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    editingNote: Note?,
    onAddNote: (String) -> Unit,
    onUpdateNote: (String, String) -> Unit,
    onStartEditing: (Note) -> Unit,
    onCancelEditing: () -> Unit,
    onDeleteNote: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    var noteInput by rememberSaveable { mutableStateOf("") }
    var noteToDelete by remember { mutableStateOf<Note?>(null) }

    LaunchedEffect(editingNote) {
        if (editingNote != null) {
            noteInput = editingNote.text
        }
    }

    if (noteToDelete != null) {
        AlertDialog(
            onDismissRequest = { noteToDelete = null },
            title = {
                Text(
                    text = stringResource(R.string.delete_dialog_title),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.delete_dialog_message),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val target = noteToDelete
                        if (target != null) {
                            onDeleteNote(target.id)
                        }
                        noteToDelete = null
                    }
                ) {
                    Text(
                        text = stringResource(R.string.delete_confirm),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { noteToDelete = null }) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.spacingMedium)
    ) {
        // Üst Başlık ve Ayarlar Butonu
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.notes_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            TextButton(onClick = onNavigateToSettings) {
                Text(
                    text = stringResource(R.string.settings_title),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        // Arama Alanı
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.spacingSmall),
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                label = { Text(stringResource(R.string.search_hint)) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            if (searchQuery.isNotEmpty()) {
                TextButton(onClick = { onSearchQueryChange("") }) {
                    Text(
                        text = stringResource(R.string.clear_search),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

        // Not Ekleme / Düzenleme Alanı
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.spacingMedium),
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = noteInput,
                onValueChange = { noteInput = it },
                label = {
                    Text(
                        text = if (editingNote != null) {
                            stringResource(R.string.edit_note)
                        } else {
                            stringResource(R.string.note_input_hint)
                        }
                    )
                },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            if (editingNote != null) {
                Button(
                    onClick = {
                        if (noteInput.isNotBlank()) {
                            onUpdateNote(editingNote.id, noteInput)
                            noteInput = ""
                        }
                    }
                ) {
                    Text(text = stringResource(R.string.update_note))
                }
                TextButton(
                    onClick = {
                        onCancelEditing()
                        noteInput = ""
                    }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            } else {
                Button(
                    onClick = {
                        if (noteInput.isNotBlank()) {
                            onAddNote(noteInput)
                            noteInput = ""
                        }
                    }
                ) {
                    Text(text = stringResource(R.string.add_note))
                }
            }
        }

        // Not Listesi veya Boş Durum
        if (notes.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (searchQuery.isNotBlank()) {
                        stringResource(R.string.no_search_results)
                    } else {
                        stringResource(R.string.empty_notes)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
            ) {
                items(notes, key = { it.id }) { note ->
                    NoteItem(
                        note = note,
                        onEdit = { onStartEditing(note) },
                        onDelete = { noteToDelete = note }
                    )
                }
            }
        }
    }
}

/**
 * Tekil not kartı bileşeni.
 */
@Composable
fun NoteItem(
    note: Note,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formattedDate = remember(note.createdAt) {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        dateFormat.format(Date(note.createdAt))
    }

    Card(
        shape = RoundedCornerShape(Dimens.cardCornerRadius),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.cardElevation),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.spacingMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = Dimens.spacingExtraSmall)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spacingExtraSmall)) {
                TextButton(onClick = onEdit) {
                    Text(
                        text = stringResource(R.string.edit_note),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                TextButton(onClick = onDelete) {
                    Text(
                        text = stringResource(R.string.delete_note),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Preview(name = "NotesScreen Light", showBackground = true)
@Composable
private fun NotesScreenPreviewLight() {
    AgyTheme(darkTheme = false) {
        Surface {
            NotesScreen(
                notes = listOf(
                    Note(id = "1", text = "Alışveriş listesi hazırla"),
                    Note(id = "2", text = "Android Gradle Plugin sürümünü kontrol et")
                ),
                searchQuery = "",
                onSearchQueryChange = {},
                editingNote = null,
                onAddNote = {},
                onUpdateNote = { _, _ -> },
                onStartEditing = {},
                onCancelEditing = {},
                onDeleteNote = {},
                onNavigateToSettings = {}
            )
        }
    }
}

@Preview(name = "NotesScreen Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun NotesScreenPreviewDark() {
    AgyTheme(darkTheme = true) {
        Surface {
            NotesScreen(
                notes = listOf(
                    Note(id = "1", text = "Alışveriş listesi hazırla"),
                    Note(id = "2", text = "Android Gradle Plugin sürümünü kontrol et")
                ),
                searchQuery = "Gradle",
                onSearchQueryChange = {},
                editingNote = null,
                onAddNote = {},
                onUpdateNote = { _, _ -> },
                onStartEditing = {},
                onCancelEditing = {},
                onDeleteNote = {},
                onNavigateToSettings = {}
            )
        }
    }
}

@Preview(name = "NoteItem Light", showBackground = true)
@Composable
private fun NoteItemPreviewLight() {
    AgyTheme(darkTheme = false) {
        NoteItem(
            note = Note(id = "1", text = "Örnek not metni"),
            onEdit = {},
            onDelete = {}
        )
    }
}

@Preview(name = "NoteItem Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun NoteItemPreviewDark() {
    AgyTheme(darkTheme = true) {
        NoteItem(
            note = Note(id = "1", text = "Örnek not metni"),
            onEdit = {},
            onDelete = {}
        )
    }
}
