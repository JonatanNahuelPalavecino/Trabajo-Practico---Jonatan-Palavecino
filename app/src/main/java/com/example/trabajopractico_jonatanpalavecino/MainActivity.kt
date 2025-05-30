package com.example.trabajopractico_jonatanpalavecino

import android.os.Bundle
import android.widget.Toast
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
import com.example.trabajopractico_jonatanpalavecino.models.UserStorage

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private var isLoggedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //seteo del toolbar
        setSupportActionBar(binding.toolbar)

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //funcion del evento al click de los botones del sidebar
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> goToFragment(HomeFragment(), "Inicio")
                R.id.nav_nosotros -> goToFragment(NosotrosFragment(), "Nosotros")
                R.id.nav_productos -> goToFragment(ProductosFragment(), "Productos")
                R.id.nav_carrito -> goToFragment(CarritoFragment(), "Mi Carrito")
                R.id.nav_auth -> {
                    if (isLoggedIn) {
                        //Si el usuario marcó "mantener sesion" el btn dirá "cerrar sesión", la acción que realiza es...
                        UserStorage.toggleSession(this, false)
                        isLoggedIn = false
                        updateAuthMenuTitle()
                        Toast.makeText(this, "Sesión cerrada con éxito", Toast.LENGTH_LONG).show()
                        goToFragment(HomeFragment(), "Inicio")
                    } else {
                        //Si el usuario cerró sesión, el btn dirá "Inicio Sesión"...
                        goToFragment(LoginFragment(), "Inicio de Sesión")
                    }
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }

        //Verificamos el estado de "mantener sesión" del usuario y seteamos la variable isLoggedIn con ese valor
        isLoggedIn = UserStorage.isLoggedIn(this)
        updateAuthMenuTitle()
        goToFragment(HomeFragment(), "Inicio")
    }

    //funcion que permite la navegacion entre fragments
    fun goToFragment(fragment: androidx.fragment.app.Fragment, titleToolbar: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        supportActionBar?.title = titleToolbar
    }

    //funcion que actualiza el texto del btn del sidebar
    fun updateAuthMenuTitle() {
        val menu = binding.navView.menu
        val authItem = menu.findItem(R.id.nav_auth)
        authItem.title = if (isLoggedIn) "Cerrar Sesión" else "Inicio Sesión"
    }

    //funcion utilizada por Login y RegisterFragment que se ejecuta cuando el usuario inicia sesión o se registra
    fun onUserLoggedIn() {
        isLoggedIn = true
        updateAuthMenuTitle()
    }
}