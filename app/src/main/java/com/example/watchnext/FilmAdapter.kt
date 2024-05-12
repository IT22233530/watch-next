package com.example.watchnext

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class FilmAdaptur (private var films:List<Film>,context: Context): RecyclerView.Adapter<FilmAdaptur.FilmViewHolder>(){

    private val db: FilmdatabaseHelper = FilmdatabaseHelper(context)

    class FilmViewHolder(filmView: View): RecyclerView.ViewHolder(filmView){
        val fnameview : TextView = filmView.findViewById(R.id.fnameView)
        val category : TextView = filmView.findViewById(R.id.categoryView)
        val rating : TextView = filmView.findViewById(R.id.imbd)
        val discription : TextView = filmView.findViewById(R.id.discription)

        val udatebtn : ImageView = filmView.findViewById(R.id.updateButton)

        val deletebtn : ImageView = filmView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_items,parent,false)
        return FilmViewHolder(view)
    }

    override fun getItemCount(): Int = films.size

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = films[position]
        holder.fnameview.text = film.fName
        holder.category.text = film.fCategory
        holder.rating.text = film.rating.toFloat().toString()
        holder.discription.text = film.description

        holder.udatebtn.setOnClickListener{
            val intent = Intent(holder.itemView.context,UpdateFilmActivity::class.java).apply {
                putExtra("film_id",film.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deletebtn.setOnClickListener{
            db.deleteFilm(film.id)
            refreshData(db.getAllfilms())
            Toast.makeText(holder.itemView.context,"Deleted",Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newFilms:List<Film>){
        films = newFilms
        notifyDataSetChanged()
    }
}