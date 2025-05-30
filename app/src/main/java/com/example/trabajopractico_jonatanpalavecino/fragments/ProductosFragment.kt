package com.example.trabajopractico_jonatanpalavecino.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.trabajopractico_jonatanpalavecino.R
import com.example.trabajopractico_jonatanpalavecino.databinding.FragmentProductosBinding


class ProductosFragment : Fragment() {

    private lateinit var binding: FragmentProductosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //creacion de lista para las opciones del spinner
        val vinos = listOf("Todos los Productos", "Albariño", "Albillo", "Crianza")

        //adaptar la lista, el contexto y la vista al spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, vinos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        //funcion del evento del spinner
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> {
                        binding.productOneCard.visibility = View.VISIBLE
                        binding.productTwoCard.visibility = View.VISIBLE
                        binding.productThreeCard.visibility = View.VISIBLE
                    }
                    1 -> {
                        binding.productOneCard.visibility = View.VISIBLE
                        binding.productTwoCard.visibility = View.GONE
                        binding.productThreeCard.visibility = View.GONE
                    }
                    2 -> {
                        binding.productOneCard.visibility = View.GONE
                        binding.productTwoCard.visibility = View.VISIBLE
                        binding.productThreeCard.visibility = View.GONE
                    }
                    3 -> {
                        binding.productOneCard.visibility = View.GONE
                        binding.productTwoCard.visibility = View.GONE
                        binding.productThreeCard.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.productOneCard.visibility = View.VISIBLE
                binding.productTwoCard.visibility = View.VISIBLE
                binding.productThreeCard.visibility = View.VISIBLE
            }

        }
    }
}