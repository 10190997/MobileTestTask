package com.example.mobiletesttask.ui.details

import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.mobiletesttask.R
import com.example.mobiletesttask.databinding.FragmentDetailsBinding
import com.example.mobiletesttask.network.Movie
import com.example.mobiletesttask.ui.overview.OverviewViewModel

class DetailsFragment : Fragment() {
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var movie: Movie
    private val viewModel: OverviewViewModel by viewModels()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun bind(movie: Movie) {
        binding.apply {
            val imgUrl = movie.poster.url
            imgUrl.let {
                val uri = imgUrl.toUri().buildUpon().scheme("https").build()
                posterImage.load(uri) {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_broken_image)
                }
            }
            tvTitle.text = movie.name
            tvDescription.text = movie.description
            tvGenres.text = Html.fromHtml(
                binding.root.resources.getString(
                    R.string.genres,
                    movie.genres.joinToString(", ")
                ), FROM_HTML_MODE_COMPACT
            )
            tvCountries.text = Html.fromHtml(
                binding.root.resources.getString(
                    R.string.countries,
                    movie.countries.joinToString(", ")
                ), FROM_HTML_MODE_COMPACT
            )
            tvYear.text = Html.fromHtml(
                binding.root.resources.getString(
                    R.string.year,
                    movie.year.toString()
                ), FROM_HTML_MODE_COMPACT
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        viewModel.getMovie(id).observe(this.viewLifecycleOwner) {
            movie = it
            bind(movie)
        }
    }
}