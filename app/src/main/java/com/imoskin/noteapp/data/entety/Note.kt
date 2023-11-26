package com.imoskin.noteapp.data.entety

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.imoskin.noteapp.domain.model.NoteModel
import java.io.Serializable

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "text")
    var content: String
) : Serializable {

    fun toModel() : NoteModel {
        return NoteModel (id, title, content)
    }

}