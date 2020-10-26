package com.example.petmate.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.postfeed.PostFeed
import com.example.petmate.postfeed.PostFeedApi
import kotlinx.android.synthetic.main.activity_my_pets.*
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_feed.refreshLayout
import kotlinx.android.synthetic.main.post_feed.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedFragment : Fragment() {

    private var list: MutableList<PostFeed> = ArrayList<PostFeed>()
    private lateinit var adapter: PostFeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*/** Adding values **/
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


        Log.v("list", "list==$list");*/

        /*recyclerViewFeedPage.layoutManager =
            LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        adapter = PostFeedAdapter(activity, list);
        recyclerViewFeedPage.adapter = adapter*/

        refreshLayout.setOnRefreshListener {
            fetchMyPost()
        }

        fetchMyPost()
    }

    //class PostFeedAdapter(val context: FragmentActivity?, private val data: MutableList<PostFeed>) :
    class PostFeedAdapter(
        //val context: List<PostFeed>,
        //private val myPostFeed: MutableList<PostFeed>
        private val myPostFeed: List<PostFeed>
    ) :
    //RecyclerView.Adapter<PostFeedAdapter.ViewHolder>()
        RecyclerView.Adapter<PostFeedAdapter.MyPostFeedViewHolder>() {

        override fun getItemCount(): Int {
            return myPostFeed.size
        }

        /*class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
            fun bindItems(map: PostFeed) {
                itemView.user_name.text = map.id
                itemView.post_description.text = map.image
            }

        }*/

        class MyPostFeedViewHolder(val view: View) : RecyclerView.ViewHolder(view)

        /*override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItems(myPostFeed[position])
        }*/

        override fun onBindViewHolder(holder: MyPostFeedViewHolder, position: Int) {
            val myPostFeed = myPostFeed[position]

            holder.view.user_name.text = myPostFeed.title
            holder.view.post_venue.text = myPostFeed.type
            holder.view.post_description.text = myPostFeed.language
            holder.view.post_time.text = myPostFeed.id

            Glide.with(holder.view.context)
                .load(myPostFeed.image)
                .into(holder.view.user_profile_image)

            Glide.with(holder.view.context)
                .load(myPostFeed.image)
                .into(holder.view.post_image)

        }

        /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.post_feed, parent, false)
            return ViewHolder(v)
        }*/
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostFeedViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.post_feed, parent, false)
            return MyPostFeedViewHolder(v)
        }
    }  //end of onCreate method

    private fun fetchMyPost() {
        refreshLayout.isRefreshing = true
        PostFeedApi().getMyPost().enqueue(object : Callback<List<PostFeed>> {
            override fun onFailure(call: Call<List<PostFeed>>, t: Throwable) {
                refreshLayout.isRefreshing = false
                //Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<PostFeed>>,
                response: Response<List<PostFeed>>
            ) {
                refreshLayout.isRefreshing = false
                val myPostFeed = response.body()

                myPostFeed?.let {
                    showMyPostFeed(it)
                }
            }
        })
    }

    @SuppressLint("WrongConstant")
    private fun showMyPostFeed(myPostFeed: List<PostFeed>) {
        recyclerViewFeedPage.layoutManager =
            LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        //adapter = PostFeedAdapter(activity, list);
        adapter = PostFeedAdapter(myPostFeed);
        recyclerViewFeedPage.adapter = adapter
    }
} //end of Fragment
