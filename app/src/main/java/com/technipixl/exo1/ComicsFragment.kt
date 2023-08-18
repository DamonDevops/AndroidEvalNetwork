package com.technipixl.exo1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technipixl.exo1.comicsRecycler.ComicsViewAdapter
import com.technipixl.exo1.databinding.FragmentComicsBinding
import com.technipixl.network.ComicItems
import com.technipixl.network.ComicsList
import com.technipixl.network.MarvelServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ComicsFragment : Fragment(), ComicsViewAdapter.OnSelectItem {

    private var binding: FragmentComicsBinding? = null
    private lateinit var model :ComicsList
    private lateinit var character :ComicItems
    private val comicsService by lazy { MarvelServiceImpl() }
    private var job :Job? = null
    private val args: ComicsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComicsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getComicsListAsync()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setupRecycler(){
        val recyclerView = binding?.comicsList

        recyclerView?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView?.adapter = ComicsViewAdapter(model, this)
    }

    private fun setupHeadView(){
        val url = "${character.thumbnail?.path}.${character.thumbnail?.extension}"
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.image_placeholder)
            .into(binding?.headImage)

        binding?.characterName?.text = character.name
    }

    private fun getComicsListAsync(){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = comicsService.getComics(args.characterId.toString())
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        model = ComicsList(mutableListOf())
                        response.body()?.data?.results?.forEach { result ->
                            result.comics.comicList.forEach{ res ->
                                model.comicList.add(res)
                            }
                        }
                        response.body()?.data?.results?.first()?.let {
                            character = it
                        }
                    }
                    setupRecycler()
                    setupHeadView()
                } catch (e : HttpException){
                    print(e)
                } catch (e :Throwable){
                    print(e)
                }
            }
        }
    }
    private fun cancelJob(){
        job?.cancel()
    }

    override fun onItemClicked(id: String) {
        val destination = ComicsFragmentDirections.actionComicsFragmentToComicsDetailFragment(id)
        findNavController().navigate(destination)
    }
}