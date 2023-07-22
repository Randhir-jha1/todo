package com.appdev.todo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.appdev.todo.R
import com.appdev.todo.data.Note
import com.appdev.todo.databinding.ActivityEditNoteBinding

class EditNote : AppCompatActivity() {
    lateinit var binding: ActivityEditNoteBinding
    private  var selectedNote: Note?=null
    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selectedNote = intent.getSerializableExtra("SelectedNote") as Note
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.ivBack.setOnClickListener(){
            finish()
        }
        binding.etNotesTitle.setText(selectedNote!!.title)
        binding.etNotesDes.setText(selectedNote!!.des)
        binding.llNote.setBackgroundColor(selectedNote!!.color!!)

        binding.btnupdate.setOnClickListener(){

            val editedNote = Note(selectedNote!!.id,binding.etNotesTitle.text.toString(),
                binding.etNotesDes.text.toString(),selectedNote!!.color)

            viewModel.updateNotes(editedNote)
            Toast.makeText(this,"Updated the notes", Toast.LENGTH_LONG).show()
            val mainIntent = Intent(this@EditNote,MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }

    }
}