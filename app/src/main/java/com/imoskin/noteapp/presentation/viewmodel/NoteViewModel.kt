package com.imoskin.noteapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imoskin.noteapp.domain.model.NoteModel
import com.imoskin.noteapp.domain.use_case.UseCases
import kotlinx.coroutines.launch

class NoteViewModel(private val useCases: UseCases) : ViewModel() {
    private val _notes = MutableLiveData<List<NoteModel>>()

    val notes: LiveData<List<NoteModel>>
        get() = _notes

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            val note = NoteModel(0, title = title, content = content)
            useCases.addNote(note)
        }
        loadNotes()
    }

    fun editNote(note: NoteModel) {
        viewModelScope.launch {
            useCases.updateNote(note)
        }
        loadNotes()
    }

    fun deleteNote(note: NoteModel) {
        viewModelScope.launch {
            useCases.deleteNote(note)
        }
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            val notes = useCases.getNotes()
            _notes.value = notes
        }
    }
}