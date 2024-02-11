package com.example.lesson19

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson19.databinding.FragmentFirstBinding
import com.google.gson.Gson

class FirstFragment : Fragment(), TasksAdapter.OnTaskClicked {

    private var binding: FragmentFirstBinding? = null
    var adapter: TasksAdapter? = null
    private var tasksListJson = ""
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREFS",
            Context.MODE_PRIVATE)
        setOnClickListeners()
        initTasksAdapter()

        if (sharedPreferences!!.getString("TASKS_JSON", "") != "") {

            val tasksListJson = Gson().fromJson(sharedPreferences!!.getString("TASKS_JSON", ""),
                Array<Task>::class.java)

            adapter?.tasksList = tasksListJson.toMutableList()
        }

        if (arguments != null) {

            if (arguments?.getString("TASK_TEXT", "") != "") {

                val tasksListFromJson = Gson().fromJson(requireArguments().getString("TASKS_LIST"),
                    Array<Task>::class.java)

                adapter?.tasksList = tasksListFromJson.toMutableList()

                adapter?.tasksList?.add(Task(false, requireArguments()
                    .getString("TASK_TEXT", ""), requireArguments()
                    .getString("TASK_DESCRIPTION", "")))

                tasksListJson = Gson().toJson(adapter?.tasksList)
            }
        }
    }

    private fun initTasksAdapter() {
        adapter = TasksAdapter()
        adapter?.onTaskClickedImpl = this
        binding?.rvToDoList?.adapter = adapter
        binding?.rvToDoList?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setOnClickListeners() {
        binding?.btnAddTask?.setOnClickListener {

            tasksListJson = Gson().toJson(adapter?.tasksList)

            val bundle = Bundle()
            bundle.putString("TASKS_LIST", tasksListJson)

            val fragment = AddTaskFragment()
            fragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.fragmentContainer, fragment).commit()
        }
    }

    override fun onStop() {
        tasksListJson = Gson().toJson(adapter?.tasksList)
        sharedPreferences?.edit()?.putString("TASKS_JSON", tasksListJson)?.apply()

        Log.e("TAG", "On DestroyView $tasksListJson")
        super.onStop()
    }

    override fun onTaskClicked(taskName: String, taskDescription: String) {

        val bundle = Bundle()
        bundle.putString("TASK_NAME", taskName)
        bundle.putString("TASK_DESCRIPTION", taskDescription)
        bundle.putBoolean("AFTER_TASK_IS_CLICKED", true)

        val fragment = AddTaskFragment()
        fragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.fragmentContainer, fragment).addToBackStack("").commit()
    }
}