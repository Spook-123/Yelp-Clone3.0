package com.example.yelpclone_1.Yelp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yelpclone_1.PLACE
import com.example.yelpclone_1.R
import com.example.yelpclone_1.SEARCH_TERM
import com.example.yelpclone_1.adapter.RestaurantsAdapter
import com.example.yelpclone_1.data.YelpRestaurant
import com.example.yelpclone_1.data.YelpSearchResult
import com.example.yelpclone_1.data.YelpService
import kotlinx.android.synthetic.main.activity_yelp_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG = "YelpMainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "n-otUXZisITwIFDZpn9YiOWosPD_ZbEFLMHHTVD1eiT7OcdN29l2P8NQRd9oGLEBwsIOiHuY4maKNOKVg_e6Lbp4AuU1oQist33V_boTf_SNwYrpe9jSw1TeqgcrYnYx"
class YelpMainActivity : AppCompatActivity(), RestaurantsAdapter.OnItemClicked {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yelp_main)


        val restaurants = mutableListOf<YelpRestaurant>()
        val adapter = RestaurantsAdapter(this,restaurants,this)
        rvYelp.adapter = adapter
        rvYelp.layoutManager = LinearLayoutManager(this)


        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

        val searchTerm = intent.getStringExtra(SEARCH_TERM)
        val place = intent.getStringExtra(PLACE)

        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY",searchTerm!!,place!!,false).enqueue(object : Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.i(TAG,"onResponse $response")
                val body = response.body()
                if(body == null) {
                    Log.i(TAG,"Did not receive valid response body from Yelp Api...exiting")
                    return
                }
                restaurants.addAll(body.restaurants)
                adapter.notifyDataSetChanged()


            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG,"onFailure $t")

            }



        })

    }

    override fun onItemClick(item: YelpRestaurant) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }


}