package com.example.lesson19

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson19.databinding.TaskItemBinding

class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

     var tasksList = mutableListOf<Task>()
     var onTaskClickedImpl: OnTaskClicked? = null

     inner class TasksViewHolder(val binding: TaskItemBinding) : RecyclerView.ViewHolder(
          binding.root)

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {

          return TasksViewHolder(TaskItemBinding.inflate(LayoutInflater.from(parent.context),
               parent, false))
     }

     override fun getItemCount(): Int {

          return tasksList.size
     }

     override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
          holder.binding.tvTask.text = tasksList[position].taskName

          if (!tasksList[position].isChecked) {

               holder.binding.checkBox.isChecked = false
          } else {

               holder.binding.checkBox.isChecked = true
          }

          holder.binding.ivDeleteTask.setOnClickListener {

               tasksList.removeAt(position)
               notifyItemRemoved(position)
               notifyItemRangeChanged(position, tasksList.size)
          }
          holder.binding.checkBox.setOnClickListener {

               if (!tasksList[position].isChecked) {

                    tasksList[position].isChecked = true
               } else {

                    tasksList[position].isChecked = false
               }
          }
          holder.binding.root.setOnClickListener {

               onTaskClickedImpl?.onTaskClicked(tasksList[position].taskName,
                    tasksList[position].taskDescription)
          }
     }

     interface OnTaskClicked {

          fun onTaskClicked(taskName: String, taskDescription: String)
     }
}