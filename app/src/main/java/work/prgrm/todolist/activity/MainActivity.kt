package work.prgrm.todolist.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import work.prgrm.todolist.R
import work.prgrm.todolist.adapter.TodoAdapter
import work.prgrm.todolist.entity.TodolistWithCategoriesEntity
import work.prgrm.todolist.repository.TodolistWithCategoriesRepository


class MainActivity : AppCompatActivity(),AdapterView.OnItemLongClickListener,AdapterView.OnItemClickListener {
    private var recyclerTodo:RecyclerView? = null
    private var items = mutableListOf<TodolistWithCategoriesEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val root = findViewById<View>(R.id.activity_main).parent as ViewGroup
        val toolbar: Toolbar = LayoutInflater.from(this).inflate(R.layout.toolbar, root, false) as Toolbar

        root.addView(toolbar, 0)
//        val toolbar:Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout:DrawerLayout = findViewById(R.id.drawer_layout)
        val actionBarDrawerToggle:ActionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name)
        actionBarDrawerToggle.drawerArrowDrawable.color = getColor(R.color.white)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        recyclerTodo = findViewById(R.id.recycler_todo)

        val addTodoButton: Button = findViewById(R.id.add_todo_button)
        val toCategoryButton: Button = findViewById(R.id.category_button)

        addTodoButton.setOnClickListener{
            val intent = Intent(this,AddTodoActivity().javaClass)
            startActivity(intent)
        }

        toCategoryButton.setOnClickListener{
            val intent = Intent(this,ListCategoryActivity().javaClass)
            startActivity(intent)
        }
        recyclerTodo?.adapter = TodoAdapter(items)
        recyclerTodo?.layoutManager = LinearLayoutManager(this)

        Log.i("onCreate ","start")
    }

    override fun onStart() {
        super.onStart()
        //val todoRepository = TodoRepository()
        val todolistWithCategoriesRepository = TodolistWithCategoriesRepository()

        CoroutineScope(Dispatchers.Default).launch {
            items = todolistWithCategoriesRepository.selectAll()
            CoroutineScope(Dispatchers.Main).launch {
                recyclerTodo?.adapter = TodoAdapter(items)
                val adapterTodo = recyclerTodo?.adapter
                adapterTodo?.notifyDataSetChanged()
                recyclerTodo?.setOnLongClickListener{
                    true
                }
            }
        }
        Log.i("onStart ","start")
    }

    override fun onResume(){
        super.onResume()

        Log.i("onResume ","start")
        if(items.size == 0){
            Log.i("daf","dfasdfadsf")
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("onRestart ","start")

    }

    override fun onPause() {
        super.onPause()
        Log.i("onPause ","start")

    }

    override fun onStop() {
        super.onStop()
        Log.i("onStop ","start")

    }
    override fun onDestroy() {
        super.onDestroy()
        this.recyclerTodo?.adapter = null
        this.recyclerTodo = null
        Log.i("onDestroy ","start")
    }


    override fun onItemLongClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long): Boolean {
        Log.i("onItemLongClicked",p1.toString())
        return true
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.i("onItemClicked",p1.toString())
    }
}
