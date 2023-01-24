package com.example.kotlinflowapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinflowapp.Adapter.User_adapter
import com.example.kotlinflowapp.Helpers.checkConnect
import com.example.kotlinflowapp.Model.Post_response
import com.example.kotlinflowapp.Model.User_data_response
import com.example.kotlinflowapp.ViewModel.PostViewModel
import com.example.kotlinflowapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var postViewModel: PostViewModel

    private lateinit var postList: List<Post_response>
    private var userList: ArrayList<User_data_response> = ArrayList()
    private var searchUserList: ArrayList<User_data_response> = ArrayList()
    private lateinit var userAdapter: User_adapter
    var limit = 50
    var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()

        checkNetwork()


        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }

            override fun afterTextChanged(s: Editable?) {
                //Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT).show()
                if (s != null) {
                    if (s.isNotEmpty()) {
                        //Toast.makeText(this@MainActivity, s.length.toString(), Toast.LENGTH_SHORT).show()
                        var setList: MutableSet<User_data_response> = HashSet()
                        for (i in 0 until userList.size) {
                            if (userList[i].firstName?.toLowerCase()
                                    ?.contains(
                                        s.toString().toLowerCase()
                                    ) == true || userList[i].lastName?.toLowerCase()
                                    ?.contains(s.toString().toLowerCase()) == true
                            ) {
                                setList.add(userList[i])
                            }
                        }
                        searchUserList.clear()
                        searchUserList.addAll(setList)
                        setDataInToList(searchUserList)
//
//                    Log.d("dataxx", "set size:: ${searchUserList.size.toString()}")
                    } else {
//                        Toast.makeText(this@MainActivity, "Nothing to search", Toast.LENGTH_SHORT)
//                            .show()
                        setDataInToList(userList)
                    }

                } else {
                    Toast.makeText(this@MainActivity, "Nothing to search", Toast.LENGTH_SHORT)
                        .show()
//                    setDataInToList(userList)
                }
            }


        })
    }

    private fun checkNetwork() {
        lifecycleScope.launchWhenStarted {
            checkConnect().collect {
                if (it) {
                    Toast.makeText(this@MainActivity, "connected", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "not connected", Toast.LENGTH_SHORT).show()
                }
            }
        }
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

            setDataInToList(userList)

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setDataInToList(userList: ArrayList<User_data_response>) {

        userAdapter = User_adapter(userList)
        userAdapter.notifyDataSetChanged()
        binding.itemView.adapter = userAdapter
        //Log.d("dataxx", limit + " " + page + " " + "size:: ${it.data.size.toString()}")

    }
}