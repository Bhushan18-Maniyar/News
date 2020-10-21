package com.example.dell.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.sql.Array


class NewsListAdapter(private val listner: NewsClicked) : RecyclerView.Adapter<NewsViewHolder>() {
    private val items: ArrayList<NewsClass> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_row, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val current = items[position]
        holder.title.text = current.title
        holder.description.text = current.description
        Glide.with(holder.itemView.context).load(current.imageUrl).into(holder.image)

    }
    fun updateNews(updatedNews: ArrayList<NewsClass>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.image)
    val title: TextView = itemView.findViewById(R.id.title)
    val description: TextView = itemView.findViewById(R.id.description)
}

interface NewsClicked{
    fun OnClicked(item: NewsClass)
}
