package com.example.dell.news

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import androidx.browser.customtabs.CustomTabsIntent


class MainActivity : AppCompatActivity(), NewsClicked {
    private lateinit var adapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        adapter = NewsListAdapter(this)
        recyclerView.adapter = adapter

//        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//
//                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
//
//                }
//            }
//        })
    }

    private fun fetchData() {
        val list: ArrayList<NewsClass> = ArrayList()
//        val url = "http://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=25c8178036d246ceb22dd2ef8b994c60"
        val url = "http://newsapi.org/v2/top-headlines?country=in&apiKey=25c8178036d246ceb22dd2ef8b994c60"
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener {
                    val newsJsonArray = it.getJSONArray("articles")
                    for (i in 0 until newsJsonArray.length()) {
                        val jsonObject = newsJsonArray.getJSONObject(i)
                        list.add(NewsClass(jsonObject.getString("title"),
                                jsonObject.getString("urlToImage"),
                                jsonObject.getString("description"),
                                jsonObject.getString("url")))
                    }
                    adapter.updateNews(list)
                },
                Response.ErrorListener {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                })

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    override fun onClicked(item: NewsClass) {
        Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show()
        val url = item.url
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }
}

