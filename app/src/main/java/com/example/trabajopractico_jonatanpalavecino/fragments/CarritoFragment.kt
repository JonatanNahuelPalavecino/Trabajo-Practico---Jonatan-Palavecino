package com.example.trabajopractico_jonatanpalavecino.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trabajopractico_jonatanpalavecino.MainActivity
import com.example.trabajopractico_jonatanpalavecino.R
import com.example.trabajopractico_jonatanpalavecino.databinding.FragmentCarritoBinding

class CarritoFragment : Fragment() {

    private lateinit var binding: FragmentCarritoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarritoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btn.setOnClickListener {
            goToFragment(ProductosFragment(), "Productos")
        }
    }

    fun goToFragment(fragment: Fragment, titleToolBar: String) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
        (activity as? MainActivity)?.supportActionBar?.title = titleToolBar
    }
}