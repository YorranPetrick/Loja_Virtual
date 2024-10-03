package com.yorran.lojavirtual.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.yorran.lojavirtual.R
import com.yorran.lojavirtual.databinding.ActivityLogInBinding
import utlis.Utils

class LogInActivity : AppCompatActivity() {

    private lateinit var logInBinding : ActivityLogInBinding
    private lateinit var email:String
    private lateinit var password:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logInBinding =  ActivityLogInBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(logInBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        logInBinding.signUnBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        logInBinding.logInBtn.setOnClickListener {
            //Obtendo os valores do EditText
            email = logInBinding.editTextEmail.text.toString()
            password = logInBinding.editTextPassword.text.toString()

            //Usando o Metodo de validação do Utils
            val validationEmail:Boolean = Utils.validation(
                email,
                this,
                logInBinding.editTextEmail
            )

            val validationPassword:Boolean = Utils.validation(
                password,
                this,
                logInBinding.editTextPassword
            )

            if (validationEmail && validationPassword){
                sigInUser(email, password)
            }


        }

    }
    private fun sigInUser(email: String, password: String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { auth->
                if (auth.isSuccessful){
                    //Log para debug
                    //Log.d("sigInUser", "Sucefful")
                    startActivity(Intent(this,HomeActivity::class.java))
                    Toast.makeText(this, "Cadastrado com Sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.addOnFailureListener { exception->
                Toast.makeText(this, "$exception", Toast.LENGTH_SHORT).show()
            }
    }
}