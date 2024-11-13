package com.yorran.lojavirtual.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.yorran.lojavirtual.R
import com.yorran.lojavirtual.databinding.ActivitySignUpBinding
import com.yorran.lojavirtual.models.DB
import utlis.Utils

class SignUpActivity : AppCompatActivity() {
    private lateinit var signUpBinding: ActivitySignUpBinding
    private val db = DB() //Inicializando a variavel com a class
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        signUpBinding.signUnBtn.setOnClickListener {

            val email = signUpBinding.editTextEmail.text.toString()
            val password = signUpBinding.editTextPassword.text.toString()
            val confirmPassword = signUpBinding.editTextConfirmPassword.text.toString()
            val name = signUpBinding.editTextName.text.toString()

            if (password == confirmPassword){
                //Validando os campos
                val validationEmail = Utils.validation(email, this, signUpBinding.editTextEmail)
                val validationPassword = Utils.validation(password, this, signUpBinding.editTextPassword)
                val validationConfirmPassword = Utils.validation(confirmPassword, this, signUpBinding.editTextConfirmPassword)
                val validationName = Utils.validation(name, this, signUpBinding.editTextName)

                if (validationEmail && validationPassword && validationConfirmPassword && validationName){
                    //Criação de conta com firebase
                    signUp(email, password, name)
                }
            }else{
                Toast.makeText(this, "Password and Confirm Password must be the same", Toast.LENGTH_LONG).show()
            }
        }
    }

    //Função para autenticação
    private fun signUp(email:String, password: String, nome: String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { Auth->
                if (Auth.isSuccessful){
                    db.salvandoDadosUsuario(nome) //Salvando o nome no BD
                    Toast.makeText(this,"SigUp is Successful", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { Exception->

                val mensagemError = when(Exception){
                    //Erro de Autenticação
                    is FirebaseAuthWeakPasswordException -> "Senha Fraca, refaça a senha"
                    is FirebaseAuthEmailException -> "Erro ao cadastrar o E-mail"
                    is FirebaseAuthUserCollisionException -> "E-mail já se encontra cadastrado"
                    else -> "SigUp is error"

                }
                Toast.makeText(this, mensagemError, Toast.LENGTH_SHORT).show()
            }
}
}