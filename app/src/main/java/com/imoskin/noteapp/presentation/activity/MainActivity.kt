package com.imoskin.noteapp.presentation.activity


import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.imoskin.noteapp.R
import com.imoskin.noteapp.data.data_source.NoteDatabase
import com.imoskin.noteapp.data.repository.NoteRepositoryImpl
import com.imoskin.noteapp.domain.model.NoteModel
import com.imoskin.noteapp.domain.use_case.UseCasesImpl
import com.imoskin.noteapp.presentation.adapter.NoteAdapter
import com.imoskin.noteapp.presentation.viewmodel.NoteViewModel
import com.imoskin.noteapp.presentation.viewmodel.NoteViewModelFactory
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: NoteViewModel
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var editedNote: NoteModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //получаем БД
        noteDatabase = NoteDatabase.newInstance(applicationContext)

        val noteDao = noteDatabase.noteDao()
        val repository = NoteRepositoryImpl(noteDao)
        val interactor = UseCasesImpl(repository)
        runBlocking {
            Log.d("MainActivity", interactor.getNotes().toString())
        }

        viewModel = ViewModelProvider(this, NoteViewModelFactory(interactor))[NoteViewModel::class.java]
        viewModel.loadNotes()

        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.addButton)

        noteAdapter = NoteAdapter(emptyList())

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteAdapter



        val editNoteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    editedNote = result.data?.getSerializableExtra("editedNote", NoteModel::class.java)!!
                } else {
                    @Suppress("DEPRECATION")
                    editedNote = result.data?.getSerializableExtra("editedNote") as NoteModel
                }

                viewModel.editNote(editedNote)
            }
        }

        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            intent.putExtra("noteId", -1000)
            editNoteLauncher.launch(intent)
        }

        noteAdapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: NoteModel) {
                val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
                intent.putExtra("noteId", note.id)
                editNoteLauncher.launch(intent)
            }
        })

        noteAdapter.setOnItemLongClickListener(object : NoteAdapter.OnItemLongClickListener {
            override fun onItemLongClick(note: NoteModel) {
                viewModel.deleteNote(note)
            }
        })

        viewModel.notes.observe(this) { notes ->
            noteAdapter.setNotes(notes)
            noteAdapter.notifyDataSetChanged()
        }

    }

    override fun onRestart() {
        super.onRestart()
        recreate();
    }


}