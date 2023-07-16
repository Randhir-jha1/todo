package com.appdev.todo.data

import androidx.lifecycle.LiveData
import com.appdev.todo.database.NoteDAO


class NoteRepository(private val  noteDAO: NoteDAO) {

    val allNotes:LiveData<List<Note>> = noteDAO.getAllNotes()

    suspend fun insert(note: Note)= noteDAO.insertNote(note)

    suspend fun delete(note: Note)= noteDAO.deleteNote(note)

    suspend fun update(note: Note)= noteDAO.updateNote(note)
}