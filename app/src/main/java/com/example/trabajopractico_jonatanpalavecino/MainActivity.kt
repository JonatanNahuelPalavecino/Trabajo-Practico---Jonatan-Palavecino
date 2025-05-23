package com.example.trabajopractico_jonatanpalavecino

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trabajopractico_jonatanpalavecino.databinding.ActivityMainBinding
import com.example.trabajopractico_jonatanpalavecino.fragments.CarritoFragment
import com.example.trabajopractico_jonatanpalavecino.fragments.HomeFragment
import com.example.trabajopractico_jonatanpalavecino.fragments.LoginFragment
import com.example.trabajopractico_jonatanpalavecino.fragments.NosotrosFragment
import com.example.trabajopractico_jonatanpalavecino.fragments.ProductosFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> goToFragment(HomeFragment(), "Inicio")
                R.id.nav_nosotros -> goToFragment(NosotrosFragment(), "Nosotros")
                R.id.nav_productos -> goToFragment(ProductosFragment(), "Productos")
                R.id.nav_carrito -> goToFragment(CarritoFragment(), "Mi Carrito")
                R.id.nav_auth -> goToFragment(LoginFragment(), "Inicio de Sesión")
            }
            binding.drawerLayout.closeDrawers()
            true
        }

        goToFragment(HomeFragment(), "Inicio")
    }

    fun goToFragment(fragment: androidx.fragment.app.Fragment, titleToolbar: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        supportActionBar?.title = titleToolbar
    }
}