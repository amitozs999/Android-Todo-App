
package com.example.mytodoapp

import android.content.Context
import android.content.DialogInterface
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuItemCompat
import androidx.core.view.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.user_layout.*
import kotlinx.android.synthetic.main.user_layout.view.*

class MainActivity : AppCompatActivity() {

    var taskList1= arrayListOf<TasksTable.Task>()
    var dbhelper=MyDbHelper(this)
    lateinit var taskdb:SQLiteDatabase
    lateinit var searchview:SearchView
    lateinit var taskadapter: TaskAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        taskdb=dbhelper.writableDatabase
        taskList1=TasksTable.getAllTasks(taskdb)
        taskadapter=TaskAdapter(taskList1)



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
        //Submit = loginDialog.findViewById(R.id.Submit) as Button


//        val tvdelete=findViewById<ImageButton>(R.user_layout.id.tvdel)
//        tvdelete.setOnClickListener {
//
//
//            TasksTable.deleteTask(taskdb)
//
//
//
//
//
//        }






    }
   inner class TaskAdapter( val List: ArrayList<TasksTable.Task>): RecyclerView.Adapter<TaskAdapter.myViewHolder>()
    {


        fun updateAdapterTask(newTaskslist:ArrayList<TasksTable.Task>)
        {
            List.clear()
            List.addAll(newTaskslist)
            notifyDataSetChanged()

        }
        inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
            val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val itemView = li.inflate(R.layout.user_layout, parent, false)
//        itemView.findViewById<ImageButton>(R.id.tvdel).setOnClickListener {
//            var v = getItem(position).id
//            tasks = TasksTable.deletSTask(tasksDb , v)
//            updateTasks(tasks)
//        }

            return myViewHolder(itemView)
        }

        override fun getItemCount(): Int  = List.size




        override fun onBindViewHolder(holder: myViewHolder, position: Int) {

            val item1 = List[position]
            holder.itemView.tvtitle.text = item1.title  //item1[position].titile
            holder.itemView.tvdesc.text = item1.desc
            holder.itemView.tvdone.isChecked=item1.done
//
//        holder.itemView.tvdel.setOnClickListener {
//            TasksTable.deleteTask(db)
//        }

            holder.itemView.tvdel.setOnClickListener {

                TasksTable.deleteTask(taskdb,item1.id)
                taskList1=TasksTable.getAllTasks(taskdb)
                updateAdapterTask(taskList1)

            }
            holder.itemView.tvdone.setOnClickListener {
                holder.itemView.tvdone.isChecked=item1.done

            }
            holder.itemView.tvedt.setOnClickListener {

                var alertdialog=AlertDialog.Builder(this@MainActivity)
                alertdialog.setTitle("Edit Todo")
                val view = layoutInflater.inflate(R.layout.dialog_dashboard, null)
                val toDoTitle = view.findViewById<EditText>(R.id.etnewtitile)
                val toDoDesc = view.findViewById<EditText>(R.id.etnewdesc)


                toDoTitle.setText(item1.title)
                toDoDesc.setText(item1.desc)

                alertdialog.setView(view)
                alertdialog.setPositiveButton("Update") { _: DialogInterface, _: Int ->
                    if (toDoTitle.text.isNotEmpty()) {
//                        holder.itemView.tvtitle.text=toDoTitle.text.toString()
//                        item1.title = toDoTitle.text.toString()
//                        holder.findViewById<TextView>(R.id.tvtitle).text= toDoTitle.run { text.toString() }

                        TasksTable.updateTask(taskdb,TasksTable.Task(null,toDoTitle.text.toString(),false,toDoDesc.text.toString()))
                        view.tvtitle.text=toDoTitle.text.toString()
                        taskList1=TasksTable.getAllTasks(taskdb)
                        updateAdapterTask(taskList1)
                        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        recyclerView.adapter=TaskAdapter(taskList1)





                    }
                }
                alertdialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

                }
                alertdialog.show()

            }
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
        var filterlist = taskList1
        var searchItem = menu!!.findItem(R.id.search_item)
        searchview=MenuItemCompat.getActionView(searchItem) as SearchView
        searchview.setOnQueryTextListener(object :SearchView.OnQueryTextListener{

            override fun onQueryTextChange(p0: String): Boolean {
                return false

            }



            override fun onQueryTextSubmit(p0: String): Boolean {
                if(p0=="") {
                    filterlist = TasksTable.getAllTasks(taskdb)
                } else {
                    filterlist = TasksTable.search(taskdb, p0)
                }
                Log.d("error",p0)


                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerView.adapter=TaskAdapter(filterlist)


                return false

            }


        })






//        searchView.imeOptions = EditorInfo.IME_ACTION_DONE;
//        val searchItem = menu.getItem(R.id.search_item)
//        if(searchItem != null){
//            val searchView = searchItem.actionView as? SearchView
//            if (searchView != null) {
//                searchView.imeOptions = EditorInfo.IME_ACTION_DONE
//            }
//
////            val editext = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
////            editext.hint = "Search here..."
//
//            searchView.setOnQueryTextListener()
//        }
//

        return super.onCreateOptionsMenu(menu)


        
    }
    override fun onBackPressed(){
        recyclerView.adapter=TaskAdapter(taskList1)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

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
            R.id.search_item -> {
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

