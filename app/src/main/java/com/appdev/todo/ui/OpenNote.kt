package com.appdev.todo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appdev.todo.R
import com.appdev.todo.data.Note
import com.appdev.todo.databinding.ActivityAddNoteBinding
import com.appdev.todo.databinding.ActivityOpenNoteBinding

class OpenNote : AppCompatActivity() {
    private lateinit var binding: ActivityOpenNoteBinding

    private  var selectedNote: Note?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOpenNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selectedNote = intent.getSerializableExtra("SelectedNote") as Note
        binding.ivBack.setOnClickListener(){
            finish()
        }
        binding.ivEdit.setOnClickListener(){

        }

        binding.tvNotesTitle.text = selectedNote?.title
        binding.tvNotesDes.text = selectedNote?.des
    }
}