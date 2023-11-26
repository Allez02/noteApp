package com.imoskin.noteapp.domain.use_case

import com.imoskin.noteapp.domain.model.NoteModel
import com.imoskin.noteapp.domain.repository.NoteRepository

class UseCasesImpl (private val repository: NoteRepository) : UseCases {

    override suspend fun getNotes(): List<NoteModel> {
        return repository.getNotes()
    }

    override suspend fun addNote(note: NoteModel) {
        return repository.addNote(note)
    }

    override suspend fun updateNote(note: NoteModel) {
        return repository.updateNote(note)
    }

    override suspend fun deleteNote(note: NoteModel) {
        return repository.deleteNote(note)
    }

}