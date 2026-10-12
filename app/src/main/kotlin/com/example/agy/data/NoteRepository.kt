package com.example.agy.data

import org.json.JSONArray
import org.json.JSONObject
import java.io.File

/**
 * Not veri modeli.
 */
data class Note(
    val id: String,
    val text: String,
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Android yerleşik org.json kütüphanesi ile filesDir/notes.json dosyasına
 * notları okuma ve yazma işlemlerini yürüten veri deposu.
 */
class NoteRepository(private val filesDir: File) {

    private val notesFile: File
        get() = File(filesDir, "notes.json")

    fun loadNotes(): List<Note> {
        val file = notesFile
        if (!file.exists()) {
            return emptyList()
        }
        return try {
            val content = file.readText(Charsets.UTF_8)
            val jsonArray = JSONArray(content)
            val list = mutableListOf<Note>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    Note(
                        id = obj.getString("id"),
                        text = obj.getString("text"),
                        createdAt = obj.optLong("createdAt", System.currentTimeMillis())
                    )
                )
            }
            list.sortedByDescending { it.createdAt }
        } catch (_: Exception) {
            emptyList()
        }
    }

    fun saveNotes(notes: List<Note>) {
        val jsonArray = JSONArray()
        for (note in notes) {
            val obj = JSONObject().apply {
                put("id", note.id)
                put("text", note.text)
                put("createdAt", note.createdAt)
            }
            jsonArray.put(obj)
        }
        notesFile.writeText(jsonArray.toString(), Charsets.UTF_8)
    }
}
