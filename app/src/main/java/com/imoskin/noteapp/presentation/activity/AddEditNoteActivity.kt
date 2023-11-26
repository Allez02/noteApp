package com.imoskin.noteapp.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.imoskin.noteapp.R
import com.imoskin.noteapp.data.data_source.NoteDao
import com.imoskin.noteapp.data.data_source.NoteDatabase
import com.imoskin.noteapp.data.entety.Note
import com.imoskin.noteapp.data.repository.NoteRepositoryImpl
import com.imoskin.noteapp.domain.use_case.UseCasesImpl
import com.imoskin.noteapp.presentation.viewmodel.NoteViewModel
import com.imoskin.noteapp.presentation.viewmodel.NoteViewModelFactory
import kotlinx.coroutines.runBlocking

class AddEditNoteActivity : AppCompatActivity() {
    private lateinit var viewModel: NoteViewModel
    private lateinit var noteEditTitle: EditText
    private lateinit var noteEditContent: EditText
    private lateinit var saveButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        val noteId = intent.getIntExtra("noteId", -1)

        noteEditTitle = findViewById(R.id.titleEditText)
        noteEditContent = findViewById(R.id.contentEditText)
        saveButton = findViewById(R.id.saveButton)

        val noteDatabase = NoteDatabase.newInstance(applicationContext)
        val noteDao = noteDatabase.noteDao()
        val repository = NoteRepositoryImpl(noteDao)
        val interactor = UseCasesImpl(repository)
        viewModel = ViewModelProvider(this, NoteViewModelFactory(interactor))[NoteViewModel::class.java]
        if(noteId != -1000) {
            val note: Note = getNoteFromDatabase(noteId, noteDao)
            noteEditTitle.setText(note.title)
            noteEditContent.setText(note.content)

            saveButton.setOnClickListener {
                val updatedTitle = noteEditTitle.text.toString()
                note.title = updatedTitle

                val updatedText = noteEditContent.text.toString()
                note.content = updatedText

                viewModel.editNote(note.toModel())
                finish()
            }
        }
        else {
            noteEditTitle.setText("")
            noteEditContent.setText("")
            saveButton.setOnClickListener {
                viewModel.addNote(noteEditTitle.text.toString(), noteEditContent.text.toString())
                finish()
            }
        }
    }

    private fun getNoteFromDatabase(noteId: Int, noteDao: NoteDao): Note {
        var noteFromDatabase: Note
        runBlocking {
            noteFromDatabase = noteDao.getNoteById(noteId)!!
        }

        return noteFromDatabase
    }
}