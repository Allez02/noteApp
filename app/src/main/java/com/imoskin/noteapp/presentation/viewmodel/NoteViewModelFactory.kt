package com.imoskin.noteapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imoskin.noteapp.domain.use_case.UseCases

class NoteViewModelFactory(private val useCases: UseCases) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {

            return NoteViewModel(useCases) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}