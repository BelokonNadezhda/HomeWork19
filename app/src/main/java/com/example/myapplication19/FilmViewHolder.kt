package com.example.myapplication19

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


//В конструктор класс передается layout, который мы создали(film_item.xml)
class FilmViewHolder(private val
                     itemView: View) : RecyclerView.ViewHolder(itemView) {

    //Привязываем View из layout к переменным
    private val title = itemView.findViewById<TextView>(R.id.title)
    private val poster = itemView.findViewById<ImageView>(R.id.poster)
    private val description = itemView.findViewById<TextView>(R.id.description)


    //В этом методе кладем данные из Film в наши View
    fun bind(film: Film) {
        //Устанавливаем заголовок
        title.text = film.title
        //Устанавливаем постер
        //Указываем контейнер, в котором будет "жить" наша картинка
        Glide.with(itemView)
            //Загружаем сам ресурс
            .load(film.poster)
            //Центруем изображение
            .centerCrop()
            //Указываем ImageView, куда будем загружать изображение
            .into(poster)
        //Устанавливаем описание
        description.text = film.description
    }                         

}