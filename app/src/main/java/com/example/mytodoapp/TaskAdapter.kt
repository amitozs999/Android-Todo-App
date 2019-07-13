

package com.example.mytodoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_layout.view.*

class TaskAdapter( val List: ArrayList<TasksTable.Task>): RecyclerView.Adapter<TaskAdapter.myViewHolder>()
{


    fun updateAdapterTask(newTaskslist:ArrayList<TasksTable.Task>)
    {
        List.clear()
        List.addAll(newTaskslist)
        notifyDataSetChanged()

    }
    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.user_layout, parent, false)
        return myViewHolder(itemView)
    }

    override fun getItemCount(): Int  = List.size

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        val item1 = List[position]
        holder.itemView.tvtitle.text = item1.title  //item1[position].titile
        holder.itemView.tvdesc.text = item1.desc
        holder.itemView.tvdone.isChecked=item1.done


    }


}