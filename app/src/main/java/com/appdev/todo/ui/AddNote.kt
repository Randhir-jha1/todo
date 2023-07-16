package com.appdev.todo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.appdev.todo.R
import com.appdev.todo.data.Note
import com.appdev.todo.databinding.ActivityAddNoteBinding
import com.appdev.todo.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class AddNote : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        binding.ivBack.setOnClickListener(){
            finish()
        }
        binding.btnAdd.setOnClickListener(){

            if (binding.etNotesTitle.text.toString().isNotEmpty() && binding.etNotesDes.text.toString().isNotEmpty())
            {
                val colorsArray = resources.getIntArray(R.array.cardColors)
                val randomInt =(0..9).random()
                val randomColor = colorsArray[randomInt]
                viewModel.addNotes(Note(0,binding.etNotesTitle.text.toString(),binding.etNotesDes.text.toString(),randomColor))
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }else{
                Snackbar.make(binding.root,"Add title and description for note ",Snackbar.LENGTH_SHORT).show()
            }
        }

    }
}