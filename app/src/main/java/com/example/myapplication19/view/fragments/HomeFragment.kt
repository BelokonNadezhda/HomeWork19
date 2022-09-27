package com.example.myapplication19.view.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication19.databinding.FragmentHomeBinding
import java.util.*

import android.view.*
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication19.*
import com.example.myapplication19.utils.AnimationHelper
import com.example.myapplication19.view.rv_adapters.FilmListRecyclerAdapter
import com.example.myapplication19.view.rv_adapters.TopSpacingItemDecoration
import com.example.myapplication19.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


class HomeFragment : Fragment() {

    private lateinit var scope: CoroutineScope
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
      get() = _binding!!

    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }

    private var filmsDataBase = listOf<Film>()
        //Используем backing field
        set(value) {
            //Если придет такое же значение, то мы выходим из метода
            if (field == value) return
            //Если пришло другое значение, то кладем его в переменную
            field = value
            //Обновляем RV адаптер
            filmsAdapter.addItems(field)
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Инициализируем объект
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(binding.homeFragmentRoot, requireActivity(), 1)
        initPullToRefresh()
        initRV()


        /*viewModel.filmsListData.observe(viewLifecycleOwner, androidx.lifecycle.Observer<List<Film>> {
           // filmsDataBase = it
            filmsAdapter.addItems(it)
        })*/
        scope = CoroutineScope(Dispatchers.IO).also { scope ->
            scope.launch {
                viewModel.filmsListData.collect {
                    withContext(Dispatchers.Main) {
                        filmsAdapter.addItems(it)
                        filmsDataBase = it
                    }
                }
            }
            scope.launch {
                for (element in viewModel.showProgressBar) {
                    launch(Dispatchers.Main) {
                        binding.progressBar.isVisible = element
                    }
                }
            }
        }

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        /*viewModel.showProgressBar.observe(viewLifecycleOwner, androidx.lifecycle.Observer<Boolean> {
            binding.progressBar.isVisible = it
        })*/

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

    private fun initPullToRefresh() {
          //Вешаем слушатель, чтобы вызвался pull to refresh
           binding.pullToRefresh.setOnRefreshListener {
            //Чистим адаптер(items нужно будет сделать паблик или создать для этого публичный метод)

            filmsAdapter.items.clear()
            //Делаем новый запрос фильмов на сервер
            viewModel.getFilms()
            //Убираем крутящиеся колечко
            binding.pullToRefresh.isRefreshing = false
        }
    }

    private fun initRV(){
        //находим наш RV

        binding.mainRecycler.apply {
            //Инициализируем наш адаптер в конструктор передаем анонимно инициализированный интерфейс,
            //оставим его пока пустым, он нам понадобится во второй части задания
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
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
        //filmsAdapter.addItems(filmsDataBase)

    }

    override fun onStop() {
        super.onStop()
        scope.cancel()
    }

}




