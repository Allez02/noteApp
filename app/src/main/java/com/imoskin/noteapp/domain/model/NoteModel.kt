package com.imoskin.noteapp.domain.model

import com.imoskin.noteapp.data.entety.Note
import java.io.Serializable

data class NoteModel(
    var id: Int,
    var title: String,
    var content: String
) : Serializable {
    fun toEntity() : Note {
        return Note (id, title, content)
    }
}