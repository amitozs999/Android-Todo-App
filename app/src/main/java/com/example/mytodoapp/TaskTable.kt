package com.example.mytodoapp

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class TasksTable {
    data class Task(
        val id: Int?,
        var title: String,
        var done: Boolean,
        var desc: String?
    )

    companion object {
        val TABLE_NAME = "tasks"

        val CMD_CREATE_TABLE = """
           create table $TABLE_NAME (
           id INTEGER PRIMARY KEY AUTOINCREMENT,
           task text,
           done BOOLEAN,
           desc text
           );
        """.trimIndent()


        fun insertTask(db: SQLiteDatabase, task: Task) {

            val taskRow = ContentValues()
            taskRow.put("task", task.title)
            taskRow.put("done", task.done)
            taskRow.put("desc",task.desc)

            db.insert(TABLE_NAME, null, taskRow)

        }

        fun deleteTask(db: SQLiteDatabase,itemId:Int):Boolean {
            db.delete(CMD_CREATE_TABLE,"id=?", arrayOf(itemId.toString()))


         return true

        }
        fun updateTask(db: SQLiteDatabase, task: Task) {

            val taskRow = ContentValues()
            taskRow.put("task", task.title)
            taskRow.put("done", task.done)
            taskRow.put("desc",task.desc)

            db.update(TABLE_NAME, taskRow, "id = ?", arrayOf(task.id.toString()))
        }

        fun getAllTasks(db: SQLiteDatabase): ArrayList<Task> {

            val taskList = ArrayList<Task>()

            val cursor = db.query(
                TABLE_NAME,
                arrayOf("id", "task", "done","desc"),
                null, null, //where
                null, // group by
                null, //having
                null //order
            )

            cursor.moveToFirst()

            val idCol = cursor.getColumnIndex("id")
            val taskCol = cursor.getColumnIndex("task")
            val doneCol = cursor.getColumnIndex("done")
            val descCol = cursor.getColumnIndex("desc")

            while (cursor.moveToNext()) {
                val task = Task(
                    cursor.getInt(idCol),
                    cursor.getString(taskCol),
                    cursor.getInt(doneCol) == 1,
                    cursor.getString(descCol)
                )

                taskList.add(task)
            }
            cursor.close()
            return taskList
        }


    }}