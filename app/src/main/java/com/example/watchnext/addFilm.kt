package com.example.watchnext

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.watchnext.databinding.ActivityAddFilmBinding
import com.example.watchnext.databinding.ActivityMainBinding

class addFilm : AppCompatActivity() {

    private lateinit var binding: ActivityAddFilmBinding
    private lateinit var db : FilmdatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FilmdatabaseHelper(this)

        binding.saveBtn.setOnClickListener{
            val fName = binding.addname.text.toString()
            val fCategory = binding.addcategory.text.toString()
            val rating = binding.addraring.text.toString().toFloatOrNull() ?: 0.0f
            val discription = binding.adddiscription.text.toString()
            val film = Film(0,fName,fCategory,rating,discription)
            db.insertFilm(film)
            finish()

            Toast.makeText(this,"Saved", Toast.LENGTH_SHORT).show()
        }
    }
}