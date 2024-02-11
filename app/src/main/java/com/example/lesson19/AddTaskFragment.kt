package com.example.lesson19

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.lesson19.databinding.FragmentAddTaskBinding

class AddTaskFragment : Fragment() {

    private var binding: FragmentAddTaskBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setOnClickListeners()

        if (arguments != null) {

            if (requireArguments().getBoolean("AFTER_TASK_IS_CLICKED", false)) {

                binding?.etTask?.setText(requireArguments().getString("TASK_NAME",
                    ""))
                binding?.etTask?.isEnabled = false
                binding?.etTask?.setTextColor(resources.getColor(R.color.black))
                binding?.etTaskDescription?.setText(requireArguments().getString("TASK_DESCRIPTION",
                    ""))
                binding?.etTaskDescription?.isEnabled = false
                binding?.etTaskDescription?.setTextColor(resources.getColor(R.color.black))
                binding?.btnAddTask?.isVisible = false
            }
        }
    }

    private fun setOnClickListeners() {
        binding?.btnAddTask?.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("TASK_TEXT", binding?.etTask?.text.toString())
            bundle.putString("TASK_DESCRIPTION", binding?.etTaskDescription?.text.toString())
            bundle.putString("TASKS_LIST", requireArguments().getString("TASKS_LIST"))

            val fragment = FirstFragment()
            fragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment).commit()
        }
    }
}