package com.imoskin.noteapp.data.repository

import android.util.Log
import com.imoskin.noteapp.data.data_source.NoteDao
import com.imoskin.noteapp.domain.model.NoteModel
import com.imoskin.noteapp.domain.repository.NoteRepository

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {

    override suspend fun getNotes(): List<NoteModel> {
        return noteDao.getNotes().map {
            it.toModel()
        }
    }

    override suspend fun addNote(note: NoteModel) {
        return noteDao.insertNote(note.toEntity())
    }

    override suspend fun updateNote(note: NoteModel) {
        return noteDao.updateNote(note.toEntity())
    }

    override suspend fun deleteNote(note: NoteModel) {
        return noteDao.deleteNote(note.toEntity())
    }
}