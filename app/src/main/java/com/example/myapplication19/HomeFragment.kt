package com.example.myapplication19

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication19.databinding.FragmentHomeBinding
import com.example.myapplication19.databinding.MergeHomeScreenContentBinding
import java.util.*

import android.transition.*
import android.view.*
import androidx.transition.Scene
import androidx.constraintlayout.widget.ConstraintSet
import java.lang.Math.hypot


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding


    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    val filmsDataBase = listOf(
        Film("Мстители: Финал ", R.drawable.film1, "After the devastating events of Avengers: Infinity War (2018), the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.", 7.7f),
        Film("Война будущего",R.drawable.film2,"A family man is drafted to fight in a future war where the fate of humanity relies on his ability to confront the past.", 6.2f),
        Film("Человек-паук: Возвращение домой",R.drawable.film3,"Peter Parker balances his life as an ordinary high school student in Queens with his superhero alter-ego Spider-Man, and finds himself on the trail of a new menace prowling the skies of New York City.", 8.1f),
        Film("Белый лотос",R.drawable.film4,"Set in a tropical resort, it follows the exploits of various guests and employees over the span of a week.", 4.2f),
        Film("Круиз по джунглям",R.drawable.film5,"Based on Disneyland's theme park ride where a small riverboat takes a group of travelers through a jungle filled with dangerous animals and reptiles but with a supernatural element.", 6.9f),
        Film("Аквамен",R.drawable.film6,"Arthur Curry, the human-born heir to the underwater kingdom of Atlantis, goes on a quest to prevent a war between the worlds of ocean and land.", 7.1f),
        Film("Лука",R.drawable.film7,"On the Italian Riviera, an unlikely but strong friendship grows between a human being and a sea monster disguised as a human.", 9.0f)
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Инициализируем объект
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(binding.homeFragmentRoot, requireActivity(), 1)

        initRV()


        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        //Подключаем слушателя изменений введенного текста в поиска
        binding.searchView.setOnQueryTextListener(object :  SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener{
            //Этот метод отрабатывает при нажатии кнопки "поиск" на софт клавиатуре
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            //Этот метод отрабатывает на каждое изменения текста
            override fun onQueryTextChange(newText: String): Boolean {
                //Если ввод пуст то вставляем в адаптер всю БД
                if (newText.isEmpty()) {
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }
                //Фильтруем список на поискк подходящих сочетаний
                val result = filmsDataBase.filter {
                    //Чтобы все работало правильно, нужно и запрос, и имя фильма приводить к нижнему регистру
                    it.title.toLowerCase(Locale.getDefault()).contains(newText.toLowerCase(Locale.getDefault()))
                }
                //Добавляем в адаптер
                filmsAdapter.addItems(result)
                return true
            }
        })
    }

    private fun initRV(){
        //находим наш RV

        binding.mainRecycler.apply {
            //Инициализируем наш адаптер в конструктор передаем анонимно инициализированный интерфейс,
            //оставим его пока пустым, он нам понадобится во второй части задания
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener{
                override fun click(film: Film) {
                    (requireActivity() as MainActivity).launchDetailsFragment(film)
                }
            })
            //Присваиваем адаптер
            adapter = filmsAdapter
            //Присвои layoutmanager
            layoutManager = LinearLayoutManager(requireContext())
            //Применяем декоратор для отступов
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
        //Кладем нашу БД в RV
        filmsAdapter.addItems(filmsDataBase)

    }

}



