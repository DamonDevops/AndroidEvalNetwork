package com.technipixl.exo1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.technipixl.exo1.databinding.FragmentComicsDetailBinding
import com.technipixl.network.ComicsList
import com.technipixl.network.DetailDatas
import com.technipixl.network.MarvelServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ComicsDetailFragment : Fragment() {

    private var binding: FragmentComicsDetailBinding? = null
    private val detailsService by lazy { MarvelServiceImpl() }
    private lateinit var details :DetailDatas
    private val args: ComicsDetailFragmentArgs by navArgs()
    private var job : Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComicsDetailBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDetailsAsync()
    }

    private fun setupView(){
        binding?.comicTitle?.text = details.title
        binding?.comicDescription?.text = details.description
        val url = "${details.image.first().path}.${details.image.first().extension}"
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.image_placeholder)
            .into(binding?.comicImage)
    }
    private fun getDetailsAsync(){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = detailsService.getDetails(args.comicsId)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.data?.results?.first()?.let {
                            details = it
                        }
                    }
                    setupView()
                } catch (e : HttpException){
                    print(e)
                } catch (e :Throwable){
                    print(e)
                }
            }
        }
    }
}