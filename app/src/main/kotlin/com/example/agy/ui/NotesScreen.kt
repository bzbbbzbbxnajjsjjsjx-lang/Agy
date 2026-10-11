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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.agy.data.Note
import com.example.agy.ui.theme.AgyTheme
import com.example.agy.ui.theme.Dimens

/**
 * Notlar ana ekranı.
 *
 * @param notes Görüntülenecek not listesi.
 * @param onAddNote Yeni not ekleme geri çağrısı.
 * @param onDeleteNote Not silme geri çağrısı.
 * @param onNavigateToSettings Ayarlar ekranına geçiş geri çağrısı.
 */
@Composable
fun NotesScreen(
    notes: List<Note>,
    onAddNote: (String) -> Unit,
    onDeleteNote: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Ekran rotasyonunda yazılan metin rememberSaveable sayesinde kaybolmaz.
    var noteInput by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.spacingMedium)
    ) {
        // Üst Başlık ve Ayarlar Butonu (İkon kütüphanesi yerine metin butonu)
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

        // Not Ekleme Alanı
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.spacingSmall),
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = noteInput,
                onValueChange = { noteInput = it },
                label = { Text(stringResource(R.string.note_input_hint)) },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
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
                    text = stringResource(R.string.empty_notes),
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
                        onDelete = { onDeleteNote(note.id) }
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
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            Text(
                text = note.text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
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
                onAddNote = {},
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
                onAddNote = {},
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
            onDelete = {}
        )
    }
}
