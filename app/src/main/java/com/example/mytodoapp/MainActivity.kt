
package com.example.mytodoapp

import android.content.DialogInterface
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var taskList1= arrayListOf<TasksTable.Task>()
    var dbhelper=MyDbHelper(this)
    lateinit var taskdb:SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        taskdb=dbhelper.writableDatabase
        taskList1=TasksTable.getAllTasks(taskdb)
        var taskadapter=TaskAdapter(taskList1)



        fabAddTodo.setOnClickListener {
            var alertdialog=AlertDialog.Builder(this)
                alertdialog.setTitle("Add New Todo")
                val view = layoutInflater.inflate(R.layout.dialog_dashboard, null)
                val toDoTitle = view.findViewById<EditText>(R.id.etnewtitile)
            val toDoDesc = view.findViewById<EditText>(R.id.etnewdesc)


                alertdialog.setView(view)
                alertdialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                    if (toDoTitle.text.isNotEmpty()) {
                        TasksTable.insertTask(taskdb,TasksTable.Task(null,toDoTitle.text.toString(),false,toDoDesc.text.toString()))
                        taskList1=TasksTable.getAllTasks(taskdb)
                        taskadapter.updateAdapterTask(taskList1)
                        recyclerView.layoutManager = LinearLayoutManager(this)
                        recyclerView.adapter=TaskAdapter(taskList1)

                    }
                }
                alertdialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

                }
                alertdialog.show()



        }






    }
    override fun onResume() {
        recyclerView.adapter=TaskAdapter(taskList1)
        recyclerView.layoutManager = LinearLayoutManager(this)
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.list_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.share -> {
                Toast.makeText(this, "share clicked", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.about -> {
                Toast.makeText(this, "share clicked", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.search -> {
                Toast.makeText(this, "share clicked", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.exit -> {
                Toast.makeText(this, "share clicked", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.sort -> {
                Toast.makeText(this, "share clicked", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)


        }
        return true
    }
}

