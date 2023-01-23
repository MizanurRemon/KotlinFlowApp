package com.example.kotlinflowapp.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinflowapp.Model.User_data_response
import com.example.kotlinflowapp.R

class User_adapter(private val userList: List<User_data_response>) :
    RecyclerView.Adapter<User_adapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var profileImage: ImageView = itemView.findViewById(R.id.profileImage)
        var nameText: TextView = itemView.findViewById(R.id.titleText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var response = userList[position]
        var pos = position+1
        holder.nameText.text = response.title + " " + response.firstName + " " + response.lastName//+" "+pos.toString()
        Glide.with(holder.itemView.context).load(response.picture).into(holder.profileImage)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}