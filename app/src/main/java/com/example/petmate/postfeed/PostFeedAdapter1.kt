package com.example.petmate.postfeed

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R

class PostFeedAdapter1(val context: FragmentActivity?, private val data: MutableList<PostFeed>) :

    RecyclerView.Adapter<PostFeedAdapter1.ViewHolder>() {
    private var list: MutableList<PostFeed> = ArrayList<PostFeed>()
    //private lateinit var adapter: FeedFragment.PostFeedAdapter

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.post_feed, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.bindItems(data[position])
        holder.adapterPosition

        list.add(PostFeed("Anisha", "9876543210", "", "", "", "", "", "", ""))
        list.add(PostFeed("Karthik", "9876543210", "", "", "", "", "", "", ""))
        list.add(PostFeed("Vino", "9876543210", "", "", "", "", "", "", ""))
        list.add(PostFeed("Percy", "95673210", "", "", "", "", "", "", ""))

        list.add(PostFeed("Aruvi", "9876543210", "", "", "", "", "", "", ""))
        list.add(PostFeed("Faritha", "9876543210", "", "", "", "", "", "", ""))
        list.add(PostFeed("Annabeth", "9876543210", "", "", "", "", "", "", ""))
        list.add(PostFeed("Jason", "95673210", "", "", "", "", "", "", ""))

        list.add(PostFeed("Leo", "9876543210", "", "", "", "", "", "", ""))
        list.add(PostFeed("Piper", "9876543210", "", "", "", "", "", "", ""))
        list.add(PostFeed("Thalia", "9876543210", "", "", "", "", "", "", ""))
        list.add(PostFeed("Frank", "95673210", "", "", "", "", "", "", ""))


        Log.v("list", "list==$list");
    }
}