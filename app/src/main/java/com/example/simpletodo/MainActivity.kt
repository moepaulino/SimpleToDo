package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // remove item from list
                listOfTasks.removeAt(position)
            // notify adapter something has changed
                adapter.notifyDataSetChanged()
                saveItems()
            }
        }

        // 1. Detect when add button is clicked
        //findViewById<Button>(R.id.button).setOnClickListener() {
            // code here, executes when button is clicked
          //  Log.i("Leo", "User clicked a button")
        //}

        loadItems()

        // look up recycler view in the layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // create adapter passing in simple user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // set up the button and input field, so that the user can add to the list
        val inputTextField = findViewById<EditText>(R.id.addTaskField)


        // Get a reference to the button
        // and then set an onClickListener
        findViewById<Button>(R.id.button).setOnClickListener {

            // grab text that has been input into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            // add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            // notify adapter that data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // reset text field
            inputTextField.setText("")

            saveItems()
        }
    }

    // Save data that was input
    // Save data by writing/reading to file

    // create file creation method
    fun getDataFile() : File {
        // every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    // Load the items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // Save items by writing them into our data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

}