package com.example.kotlinflowapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinflowapp.Adapter.User_adapter
import com.example.kotlinflowapp.Model.Post_response
import com.example.kotlinflowapp.Model.User_data_response
import com.example.kotlinflowapp.ViewModel.PostViewModel
import com.example.kotlinflowapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var postViewModel: PostViewModel

    private lateinit var postList: List<Post_response>
    private var userList: ArrayList<User_data_response> = ArrayList()
    private lateinit var userAdapter: User_adapter
    var limit = 10
    var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()
    }

    private fun initView() {
        binding.itemView.setHasFixedSize(true)
        binding.itemView.layoutManager = LinearLayoutManager(this)

        postViewModel =
            ViewModelProvider(this)[PostViewModel::class.java] //indexing operator used in lieu of get

//        postViewModel.getPost()
//        postViewModel.responseLiveData.observe(this, Observer {
//            postList = it
//            Log.d("dataxx", "size:: ${postList.size.toString()}")
//        })

        getData(limit.toString(), page.toString())

        /*binding.itemView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(dy>0){
                    page ++
                    Toast.makeText(this@MainActivity, "down ${limit.toString()}", Toast.LENGTH_LONG).show()

                }else{
                    //Toast.makeText(this@MainActivity, "up", Toast.LENGTH_LONG).show()
                }
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                    //function that add new elements to my recycler view

                }
            }

        })*/


//        binding.nestedScrollView.post { binding.nestedScrollView.fullScroll(View.FOCUS_DOWN) }
//        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            if (scrollY > scrollX) {
//                // Toast.makeText(this, "Scrolling Down", Toast.LENGTH_SHORT).show()
//            } else {
//                //Toast.makeText(this, "Scrolling Up", Toast.LENGTH_SHORT).show()
//            }
//
//            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
//                page += 1
//                Toast.makeText(this, "Scrolling Down ${page.toString()}", Toast.LENGTH_SHORT).show()
//                get_data(limit.toString(), page.toString())
//            }
//
////            if ((v.bottom - (binding.nestedScrollView.height + binding.nestedScrollView.scrollY)) <= 0) {
////                page += 1
////                Toast.makeText(this, "Scrolling Down ${page.toString()}", Toast.LENGTH_SHORT).show()
////                get_data(limit.toString(), page.toString())
////            }
//
//        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData(limit: String, page: String) {
        postViewModel.getUsers(limit, page)
        postViewModel.responseUserLiveData.observe(this, Observer {
//userList.clear()
            userList.addAll(it.data)
            userAdapter = User_adapter(userList)
            userAdapter.notifyDataSetChanged()
            binding.itemView.adapter = userAdapter
            Log.d("dataxx", limit + " " + page + " " + "size:: ${it.data.size.toString()}")
        })
    }
}