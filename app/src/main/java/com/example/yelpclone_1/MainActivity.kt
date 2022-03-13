package com.example.yelpclone_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.yelpclone_1.Yelp.YelpMainActivity
import kotlinx.android.synthetic.main.activity_main.*


const val SEARCH_TERM = "SEARCH_TERM"
const val PLACE = "PLACE"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        btnDone.setOnClickListener {

            val searchTerm = etSearchTerm.editableText.toString()
            val place = etLocation.editableText.toString()
            if(searchTerm.trim().isEmpty() || place.trim().isEmpty()) {
                Toast.makeText(this,"Must fill field searchTerm and place",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val intent = Intent(this@MainActivity,YelpMainActivity::class.java)
            //Toast.makeText(this,"search Term = $searchTerm and place = $place",Toast.LENGTH_LONG).show()
            intent.putExtra(SEARCH_TERM,searchTerm)
            intent.putExtra(PLACE,place)
            startActivity(intent)
        }
    }
}