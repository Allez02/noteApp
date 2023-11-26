package com.imoskin.noteapp.domain.use_case

import com.imoskin.noteapp.domain.model.NoteModel

interface UseCases {
    suspend fun getNotes(): List<NoteModel>
    suspend fun addNote(note: NoteModel)
    suspend fun updateNote(note: NoteModel)
    suspend fun deleteNote(note: NoteModel)
}