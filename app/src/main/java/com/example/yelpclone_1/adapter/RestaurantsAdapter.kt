package com.example.yelpclone_1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.yelpclone_1.R
import com.example.yelpclone_1.data.YelpRestaurant
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantsAdapter(val context: Context, val restaurants: List<YelpRestaurant>,val listener:OnItemClicked):
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_restaurant,parent,false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            listener.onItemClick(restaurants[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: YelpRestaurant) {
            itemView.tvName.text = restaurant.name
            itemView.ratingBar.rating = restaurant.rating.toFloat()
            itemView.tvNumsReview.text = "${restaurant.numReviews} Reviews"
            itemView.tvAddress.text = restaurant.location.address
            itemView.tvCategory.text = restaurant.categories[0].title
            itemView.tvDistance.text = restaurant.displayDistance()
            itemView.tvPrice.text = restaurant.price
            //Picasso.get().load(restaurant.imageUrl).into(itemView.imageView)
            Glide.with(context).load(restaurant.imageUrl).apply(
                RequestOptions().transform(
                CenterCrop(), RoundedCorners(20)
            )).into(itemView.imageView)

        }

    }

    interface OnItemClicked {
        fun onItemClick(item:YelpRestaurant)

    }
}