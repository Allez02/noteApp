package com.imoskin.noteapp.domain.repository

import com.imoskin.noteapp.domain.model.NoteModel

interface NoteRepository {
    suspend fun getNotes(): List<NoteModel>
    suspend fun addNote(note: NoteModel)
    suspend fun updateNote(note: NoteModel)
    suspend fun deleteNote(note: NoteModel)
}