package com.example.trabajopractico_jonatanpalavecino.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trabajopractico_jonatanpalavecino.R
import com.example.trabajopractico_jonatanpalavecino.adapters.SlideAdapter
import com.example.trabajopractico_jonatanpalavecino.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Lista de los XML con las vistas del slide
        val slides = listOf(
            R.layout.item_slider_one,
            R.layout.item_slider_two,
            R.layout.item_slider_three,
            R.layout.item_slider_four
        )

        //montaje de las vistas al slide
        val adapter = SlideAdapter(slides)
        binding.viewPager.adapter = adapter

        //funcion que cambia automaticamente las vistas del slide
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                if (currentPage == slides.size) {
                    currentPage = 0
                }
                binding.viewPager.setCurrentItem(currentPage, true)
                currentPage++
                handler.postDelayed(this, 5000) // cada 5 segundos
            }
        }
        handler.post(runnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
    }
}