package com.example.watchnext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.watchnext.databinding.ActivityUpdateFilmBinding


class UpdateFilmActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityUpdateFilmBinding
    private lateinit var db :FilmdatabaseHelper
    private  var filmId :Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FilmdatabaseHelper(this)

        filmId = intent.getIntExtra("film_id",-1)

        if (filmId == -1){
            finish()
            return
        }

        val film = db.getFilmByID(filmId)
        binding.updatename.setText(film.fName)
        binding.updatecategory.setText(film.fCategory)
        binding.updateraring.setText(film.rating.toFloat().toString())
        binding.updateiscription.setText(film.description)

        binding.updateBtn.setOnClickListener{
            val newname = binding.updatename.text.toString()
            val newcatagory = binding.updatecategory.text.toString()
            val newrating = binding.updateraring.text.toString().toFloatOrNull() ?: 0.0f
            val newdisc = binding.updateiscription.text.toString()

            val updateFilm = Film(filmId,newname,newcatagory,newrating,newdisc)

            db.updateFilm(updateFilm)
            finish()

            Toast.makeText(this,"saved",Toast.LENGTH_SHORT).show()
        }
    }
}