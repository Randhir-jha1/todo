package com.appdev.todo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.appdev.todo.data.Note
import com.appdev.todo.data.NoteRepository
import com.appdev.todo.database.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application):AndroidViewModel(application) {


    val allNotes:LiveData<List<Note>>

    private val repository:NoteRepository

    init {
        val  dao = NoteDatabase.getDatabase(application).getNoteDeo()

        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    fun addNotes(note: Note)= viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
    fun removeNotes(note: Note)= viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun updateNotes(note: Note)= viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }


}
