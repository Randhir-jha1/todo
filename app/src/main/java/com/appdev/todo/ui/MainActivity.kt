package com.appdev.todo.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appdev.todo.R
import com.appdev.todo.adapter.INoteRVAdapter
import com.appdev.todo.adapter.NoteRVAdapter
import com.appdev.todo.data.Note
import com.appdev.todo.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), INoteRVAdapter {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NoteViewModel
    lateinit var noteAdapter:NoteRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.fabAddNote.setOnClickListener(){
            val intent = Intent(this, AddNote::class.java)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager =LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)

        noteAdapter = NoteRVAdapter(this,this)

        binding.recyclerView.adapter = noteAdapter

        viewModel.allNotes.observe(this, Observer {

            noteAdapter.submitList(it)
        })
        setUpSwipeDeleteItem()
    }

    private fun setUpSwipeDeleteItem(){
        val swipeToDelete = object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemPosition = viewHolder.adapterPosition

                val currentList = noteAdapter.currentList.toMutableList()
                val swipeItem = currentList[itemPosition]
                currentList.removeAt(itemPosition)
                viewModel.removeNotes(swipeItem)

                noteAdapter.submitList(currentList)

                val snackbar = Snackbar.make(binding.root,"Note removed",Snackbar.LENGTH_SHORT)
                snackbar.setAction("Undo"){
                    val newCurrentList = noteAdapter.currentList.toMutableList()
                    newCurrentList.add(itemPosition,swipeItem)
                    viewModel.addNotes(swipeItem)
                    noteAdapter.submitList(newCurrentList)
                }
                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.show()
            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }
    override fun onCardClicked(note: Note) {
      val openNoteIntent = Intent(this@MainActivity, OpenNote::class.java)
        openNoteIntent.putExtra("SelectedNote",note)
        startActivity(openNoteIntent)
    }
}