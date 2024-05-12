package com.example.watchnext

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchnext.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db :FilmdatabaseHelper
    private lateinit var filmAdaptur : FilmAdaptur

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FilmdatabaseHelper(this)
        filmAdaptur = FilmAdaptur(db.getAllfilms(),this)

        binding.filmrecycle.layoutManager = LinearLayoutManager(this)
        binding.filmrecycle.adapter = filmAdaptur

        binding.addBtn.setOnClickListener{
            val intent = Intent(this,addFilm::class.java)
            startActivity(intent)
        }

    }
    override fun onResume(){
        super.onResume()
        filmAdaptur.refreshData(db.getAllfilms())
    }
}