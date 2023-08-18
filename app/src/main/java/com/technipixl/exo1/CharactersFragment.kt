package com.technipixl.exo1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.exo1.characterRecycler.CharactersViewAdapter
import com.technipixl.exo1.databinding.FragmentCharactersBinding
import com.technipixl.network.CharactersResults
import com.technipixl.network.MarvelServiceImpl
import com.technipixl.network.timeStamp
import com.technipixl.network.trueKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class CharactersFragment : Fragment(), CharactersViewAdapter.OnSelectItem {
    private var binding: FragmentCharactersBinding? = null
    private lateinit var model :CharactersResults
    private val characterService by lazy { MarvelServiceImpl() }
    private var job : Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCharactersListAsync()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setupRecycler(){
        val recyclerView = binding?.charactersList

        recyclerView?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView?.adapter = CharactersViewAdapter(model, this)
    }
    private fun getCharactersListAsync(){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = characterService.getCharacters()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        model = CharactersResults(mutableListOf())
                        response.body()?.data?.results?.forEach { result ->
                            model.results.add(result)
                        }
                    }
                    setupRecycler()
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

    override fun onItemClicked(id :Int) {
        val destination = CharactersFragmentDirections.actionCharactersFragmentToComicsFragment(id.toLong())
        findNavController().navigate(destination)
    }
}