package com.yorran.lojavirtual.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yorran.lojavirtual.R
import com.yorran.lojavirtual.databinding.ActivityHomeBinding
import com.yorran.lojavirtual.dialog.PerfilDialog

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var floatingFerramentas: FloatingActionButton
    private lateinit var floatingCompras: FloatingActionButton
    private lateinit var floatingPerfil: FloatingActionButton
    private var isOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Botões do menu
        floatingFerramentas = binding.floatingFerramentas
        floatingCompras = binding.floatingCompras
        floatingPerfil = binding.floatingPerfil

        floatingFerramentas.setOnClickListener {
            if (isOpen) {
                closeFabMenu()
                floatingFerramentas.setImageResource(R.drawable.drop_up)
            } else {
                openFabMenu()
                floatingFerramentas.setImageResource(R.drawable.drop_down)
            }
        }

    }

    private fun openFabMenu() {
        floatingCompras.visibility = View.VISIBLE
        floatingPerfil.visibility = View.VISIBLE

        floatingCompras.animate().translationY(-150f).alpha(1f).setDuration(300).start()
        floatingPerfil.animate().translationY(-300f).alpha(1f).setDuration(300).start()

        isOpen = true

        onClick()
    }

    private fun closeFabMenu() {
        floatingCompras.animate().translationY(0f).alpha(0f).setDuration(300).withEndAction {
            floatingCompras.visibility = View.GONE
        }.start()
        floatingPerfil.animate().translationY(0f).alpha(0f).setDuration(300).withEndAction {
            floatingPerfil.visibility = View.GONE
        }.start()

        isOpen = false
    }

    fun onClick(){
        floatingPerfil.setOnClickListener {
            val perfilDialog = PerfilDialog(this)
            perfilDialog.iniciarDialog()
        }
    }
}